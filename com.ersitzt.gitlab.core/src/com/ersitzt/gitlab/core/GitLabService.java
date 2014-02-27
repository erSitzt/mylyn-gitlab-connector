package com.ersitzt.gitlab.core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.jar.Manifest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.util.EncodingUtil;
import org.eclipse.mylyn.commons.net.WebUtil;
import org.eclipse.mylyn.tasks.core.TaskRepository;

import java.util.Enumeration;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import com.ersitzt.gitlab.core.models.GitLabIssues;
import com.ersitzt.gitlab.core.models.GitLabModelI;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GitLabService {
    protected final GitLabRepository repository;
    protected final GitLabCredentials credentials;
    
    public static GitLabService get(TaskRepository repository) {
        return new GitLabService(GitLabRepository.createFromUrl(repository.getUrl(), repository.getProperty("API_KEY")),
                GitLabCredentials.create(repository));
    }
/*
    public static GitLabService get(String url, String username, String password) {
        return new GitLabService(GitLabRepository.createFromUrl(url, repository.getProperty("API_KEY")),
                new GitLabCredentials(username, password));
    }

*/

    /**
     * Constructor, create the client and JSON/Java interface object.
     **/
    public GitLabService(GitLabRepository repository, GitLabCredentials credentials) {
        this.repository = repository;
        this.credentials = credentials;
    }

    public boolean verifyCredentials() throws GitLabServiceException {
        doGet(new GitLabIssues());
        return true;
      }

      public GitLabIssues searchIssues(GitLabQuery query) throws GitLabServiceException {
          String uri;
          try {
              uri = GitLabRepository.API_GITLAB + "issues" + "/" + query.toQueryString(0);
              //System.err.println(uri);
          } catch (UnsupportedEncodingException e) {
              //System.err.println("Exception? Really? query was " + query);
              e.printStackTrace();
              throw new GitLabServiceException(e);
          }
          GitLabIssues issues = doGetIssuesQuery(uri);

          while (issues.getCount() > issues.getIssues().size()) {
              try {
                  uri = GitLabRepository.API_GITLAB + "issues" + "/"
                          + query.toQueryString(issues.getIssues().size());
                  //System.err.println(uri);
              } catch (UnsupportedEncodingException e) {
                  throw new GitLabServiceException(e);
              }
              issues.addMoreIssues(doGetIssuesQuery(uri).getIssues());
          }
          return issues;
      }

      private static Gson gson() {
          return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                  .setDateFormat("yyyy-MM-dd HH:mm:ss")
                  //.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS") // not UTC
                  .create();
      }

      public <T> T doDelete(GitLabModelI model) throws GitLabServiceException {
          String uri = model.buildUrl(repository) + model.getKey() + "/";
          //System.err.println("Calling uri: "  + uri);
          DeleteMethod method = new DeleteMethod(uri);
          return execute(method, credentials, model.getClass());
      }

      /** TODO Anyone know a better way to do this? I would want to infer type from argument without having to cast the response */
      public <T> List<T> doGetList(T model) throws GitLabServiceException {
          String uri = ((GitLabModelI) model).buildUrl(repository);
          GetMethod method = new GetMethod(uri);
          //Type listType = new TypeToken<List<T>>(){}.getType();
          return execute(method, credentials, ((GitLabModelI) model).getListType());
      }

      /**
       * Bitbucket REST API bug? GET requests seem to be cached on the 
       * server side and no HTTP header parameters make it behave. This 
       * method adds a random extra parameter to make the URL unique, 
       * thus avoiding the cache.
       */
      public <T> T doGetWithRandomParameter(GitLabModelI model) throws GitLabServiceException {
          String uri = model.buildUrl(repository) + model.getKey();
          uri += "?random=" + new Random().nextLong();
          //System.err.println("Calling uri: "  + uri);
          GetMethod method = new GetMethod(uri);
          return execute(method, credentials, model.getClass());
      }

      public <T> T doGet(GitLabModelI model) throws GitLabServiceException {
          String uri = model.buildUrl(repository) + model.getKey();
          //System.err.println("Calling uri: "  + uri);
          GetMethod method = new GetMethod(uri);
          return execute(method, credentials, model.getClass());
      }

      public GitLabIssues doGetIssuesQuery(String uri) throws GitLabServiceException {
          GetMethod method = new GetMethod(uri);
          return execute(method, credentials, GitLabIssues.class);
      }

      public <T extends GitLabModelI> T doPost(T model) throws GitLabServiceException {
          String uri = model.buildUrl(repository);
          //System.err.println("Calling uri: "  + uri);
          PostMethod method = new PostMethod(uri);
          if (!model.getParams().isEmpty()) {
              for (Entry<String, String> entry : model.getParams().entrySet()) {
                  method.addParameter(new NameValuePair(entry.getKey(), entry.getValue()));
              }
          }
          // We really should have task repository settings telling us what charcter set to use,
          // but Bitbucket seems to serve content in UTF-8, and so let's just suppose this is
          // OK until we get an issue reporting otherwise:
          method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
          return execute(method, credentials, model.getClass());
      }

      public <T extends GitLabModelI> T doPut(T model) throws GitLabServiceException {

          String uri = model.buildUrl(repository) + model.getKey() + "/";
          //System.err.println("Calling uri: "  + uri);
          PutMethod method = new PutMethod(uri);
          if (!model.getParams().isEmpty()) {
              try {
                  StringBuilder text = new StringBuilder();
                  for (Entry<String, String> entry : model.getParams().entrySet()) {
                      text.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                  }
                  // (UTF-8: see comment in doPost method above)
                  method.setRequestEntity(new StringRequestEntity(text.toString(), "application/x-www-form-urlencoded", "UTF-8"));
              } catch (UnsupportedEncodingException e) {
                  throw new GitLabServiceException(e);
              }
          }
          return execute(method, credentials, model.getClass());
      }

      private <T> T execute(HttpMethodBase method, GitLabCredentials credentials, Type type) throws GitLabServiceException {

          HttpClient client = new HttpClient();

          /*for (IProxyData data : ProxySelector.getProxyData(ProxySelector.getDefaultProvider())) {
              if ((data.getHost() != null) && (data.getType().equalsIgnoreCase("https"))) {
                  client.getHostConfiguration().setProxy(data.getHost(), data.getPort());                
              }            
          }   */

          // Authentication
          if (credentials != null) {
              client.getParams().setAuthenticationPreemptive(true);
              client.getState().setCredentials(new AuthScope("api.bitbucket.org", 443, AuthScope.ANY_REALM),
                      new UsernamePasswordCredentials(credentials.getUsername(), credentials.getPassword()));
              method.setDoAuthentication(true);
          }
          
          method.addRequestHeader("User-Agent", WebUtil.getUserAgent(getUserAgent()));

          try {
              int statusCode = client.executeMethod(method);

              // TODO: distinguish 401 UNAUTHORIZED from 403 FORBIDDEN
              //       (see https://confluence.atlassian.com/display/BITBUCKET/Version+1)
              if (statusCode == HttpStatus.SC_UNAUTHORIZED || statusCode == HttpStatus.SC_FORBIDDEN) {
                  // Retry with basic authorization
                  String token = credentials.getUsername() + ":" + credentials.getPassword();
                  method.setRequestHeader("Authorization", "Basic " + EncodingUtil.getAsciiString(Base64.encodeBase64(token.getBytes())));
                  statusCode = client.executeMethod(method);
              }

              if (statusCode == HttpStatus.SC_UNAUTHORIZED || statusCode == HttpStatus.SC_FORBIDDEN) {
                  // re-trying authentication obviously didn't succeed. Let's admit it.
                  // (thus avoiding a null object further down and supplying a meaningful message to the user).
                  /* GitLabRepository rep = GitLabRepository.createFromIssueUrl(method.getURI().toString()); */
                  throw new GitLabAuthorizationException( /* rep.getRepoSlug() + " " + */
                          "Repository reports an authorization problem. Are all credentials provided correctly?");
              }

              if (statusCode != HttpStatus.SC_OK
                      && (statusCode != HttpStatus.SC_NO_CONTENT && method instanceof DeleteMethod)) {
                  throw new GitLabServiceException(method.getURI() + " : " + method.getStatusText());
              }

              if (statusCode == HttpStatus.SC_BAD_REQUEST) {
                  // Bitbucket returns HTML and a 400 error if we send invalid field values
                  throw new GitLabServiceException("Error in request. Are all parameters correctly set?");
              }

              if (statusCode == HttpStatus.SC_NOT_FOUND) {
                  throw new GitLabServiceException(
                          "Could not find item, it is possible that the repositories are not in sync. Please try synchronizing your repository");
              }

              return gson().fromJson(method.getResponseBodyAsString(), type);

          } catch (HttpException e) {
              throw new GitLabServiceException(e);
          } catch (IOException e) {
              throw new GitLabServiceException(e);
          } finally {
              method.releaseConnection();
          }
      }

      /** Static field to hold our part of the user-agent string. */
      private static String useragent = null;
      /**
       * Would be more elegant if this was set statically, but we're
       * not able to access the Manifest in a static context (or does
       * anyone know how?). This method should only be used once, since
       * the {@link #useragent} field is static. See usage in
       * {@link #execute(HttpMethodBase, BitbucketCredentials, Type)}.
       */
      private String getUserAgent() {
        if (useragent != null) {
          return useragent;
        }
        Manifest manifest = null;
        String useragent = "GitLab-Connector";
        try {
          Enumeration<URL> resources = getClass().getClassLoader().getResources("META-INF/MANIFEST.MF");
          while (resources.hasMoreElements()) {
            Manifest m = new Manifest(resources.nextElement().openStream());
            String name = m.getMainAttributes().getValue("Bundle-SymbolicName"); 
            if (name != null && name.equals("com.ersitzt.gitlab.core")) {
              manifest = m;
              break;
            }
          }
        } catch (Exception E) {
      }
      if (manifest != null) {
        String v = manifest.getMainAttributes().getValue("Bundle-Version");
        if (v != null && (v.indexOf(".qualifier")>0)) {
          useragent += "/" + v.substring(0, v.indexOf(".qualifier"));
        }
      }
      return useragent;
    }
}
