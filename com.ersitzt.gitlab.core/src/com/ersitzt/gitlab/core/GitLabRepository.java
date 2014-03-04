package com.ersitzt.gitlab.core;

import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitLabRepository {
	public static final String HTTP_GITLAB = "http://srv-git.zentrale.expert.de/";
	public static final String API_GITLAB = HTTP_GITLAB + "api/v3";
	
    private static final Pattern URL_PATTERN = Pattern.compile(Pattern.quote(HTTP_GITLAB) + "([^/]+)/([^/]+)/?");
    private static final Pattern ISSUE_URL_PATTERN = Pattern.compile(Pattern.quote(API_GITLAB)
        + "([^/]+)/([^/]+)/issues/([^/]+)/?");
	private String repourl;
	private String apitoken;
	
	private final AtomicReference<Configuration> configuration = new AtomicReference<GitLabRepository.Configuration>();
	

	public GitLabRepository(String repourl, String apitoken) {
		this.repourl = repourl;
		this.setApitoken(apitoken);
	}

	public String getIssueUrl(String id) {
		return repourl + "/" + "issues" + "/" + id;
	}
    public static GitLabRepository createFromIssueUrl(String issueUrl) {
        Matcher matcher = ISSUE_URL_PATTERN.matcher(issueUrl);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid issueUrl: " + issueUrl);
        }
        return new GitLabRepository(matcher.group(1), matcher.group(2));
    }
    
    public String getUrl() {
        return HTTP_GITLAB;
    }
    
    public String getIssueApiUrl(String issueId) {
        return API_GITLAB + "/issues/" + issueId + "/";
    }

    public static String getIssueIdFromIssueUrl(String issueUrl) {
        Matcher matcher = ISSUE_URL_PATTERN.matcher(issueUrl);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid issueUrl: " + issueUrl);
        }
        return matcher.group(3);
    }
	public String getApitoken() {
		return apitoken;
	}

	public void setApitoken(String apitoken) {
		this.apitoken = apitoken;
	}

	public String getRepourl() {
		return repourl;
	}

	public void setRepourl(String repourl) {
		this.repourl = repourl;
	}

	public static GitLabRepository createFromUrl(String url, String apikey) {

		return new GitLabRepository(url, apikey);

	}
    // Required by the plugin?
    public Configuration getConfiguration() {
        return getConfiguration(false);
    }

    public Configuration getConfiguration(boolean forceLoad) {
        synchronized (this.configuration) {
            Configuration conf = configuration.get();
            if (conf == null || forceLoad) {
                conf = loadConfiguration();
                configuration.set(conf);
            }
            return conf;
        }
    }

    private Configuration loadConfiguration() {
        Configuration conf = new Configuration();
        return conf;
    }
    
    public static class Configuration {
    }

}
