package com.ersitzt.gitlab.core;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;
import org.eclipse.mylyn.tasks.core.IRepositoryQuery;

/**
 * Query object to search issues from Bitbucket repository.
 * 
 * This builds a query string based on values input into the @BitbucketRepositoryQueryPage
 * 
 * We do some voodoo here. Mylyn has a Map backing url params so we decode
 * the csv's we made in @BitbucketRepositoryQueryPage
 * 
 * @author shuji.w6e
 * @since 0.1.0
 */
public class GitLabQuery {

    private String[] statuses;
    private String[] kinds;
    private String[] versions;
    private String[] milestones;
    private String[] components;
    private String[] priorities;
    private String assignee; 
    private String title;
    
    
    public GitLabQuery() {
    }
    
    /**
     * See: http://confluence.atlassian.com/display/BBDEV/Issues
     * @return
     * @throws UnsupportedEncodingException 
     */
    public String toQueryString(int offset) throws UnsupportedEncodingException  {
        StringBuilder query = new StringBuilder();
        for (String status : statuses) {
            query.append("status=").append(URLEncoder.encode(status, "UTF-8")).append("&");
        }
        for (String kind : kinds) {
            query.append("kind=").append(URLEncoder.encode(kind, "UTF-8")).append("&");
        }
        for (String kind : versions) {
            query.append("version=").append(URLEncoder.encode(kind, "UTF-8")).append("&");
        }
        for (String kind : milestones) {
            query.append("milestone=").append(URLEncoder.encode(kind, "UTF-8")).append("&");
        }
        for (String kind : components) {
            query.append("component=").append(URLEncoder.encode(kind, "UTF-8")).append("&");
        }
        for (String kind : priorities) {
            query.append("priority=").append(URLEncoder.encode(kind, "UTF-8")).append("&");            
        }
        if (StringUtils.isNotBlank(assignee)) {
            query.append("responsible=").append(URLEncoder.encode(assignee, "UTF-8")).append("&");           
        }
        if (StringUtils.isNotBlank(title)) {
            query.append("title=~").append(URLEncoder.encode(title, "UTF-8")).append("&");
        }
        query.append("limit=").append(URLEncoder.encode("50", "UTF-8")).append("&");
        query.append("start=").append(URLEncoder.encode("" + offset, "UTF-8")).append("&");
        if (query.length() != 0) {
            query.setLength(query.length() - 1);
            query.insert(0, "?");
        }
        return query.toString();
    }
    
    public static GitLabQuery get(IRepositoryQuery query) {
    	GitLabQuery gitlabQuery = new GitLabQuery();
    	gitlabQuery.statuses = getAttributes(query, GitLab.QUERY_KEY_STATUS);
    	gitlabQuery.kinds = getAttributes(query, GitLab.QUERY_KEY_KIND);
    	gitlabQuery.versions = getAttributes(query, GitLab.QUERY_KEY_VERSION);
    	gitlabQuery.milestones = getAttributes(query, GitLab.QUERY_KEY_MILESTONE);
    	gitlabQuery.components = getAttributes(query, GitLab.QUERY_KEY_COMPONENT);
    	gitlabQuery.priorities = getAttributes(query, GitLab.QUERY_KEY_PRIORITY);
    	gitlabQuery.title = query.getAttribute(GitLab.QUERY_KEY_TITLE);
    	gitlabQuery.assignee = query.getAttribute(GitLab.QUERY_KEY_ASSIGNEE);

        return gitlabQuery;
    }
    
    static String[] getAttributes(IRepositoryQuery query, String key) {
        String values = query.getAttribute(key);
        return values != null ? values.split(",") : new String[0];
    }

}