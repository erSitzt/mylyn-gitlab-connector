package com.ersitzt.gitlab.core.models;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ersitzt.gitlab.core.GitLabRepository;
import com.google.gson.reflect.TypeToken;

public class GitLabNote implements GitLabModelI{

    private String content;
    private String commentId;
    private Date utcUpdatedOn;
    private Date utcCreatedOn;
    private GitLabUser authorInfo;
    private GitLabIssue issue;

    public GitLabNote(GitLabIssue issue) {
        this.issue = issue;
    }
    public GitLabNote(GitLabIssue issue,String content) {
        this.content = content;
        this.issue = issue;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getCommentId() {
        return commentId;
    }
    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
    public Date getUtcUpdatedOn() {
        return utcUpdatedOn;
    }
    public void setUtcUpdatedOn(Date utcUpdatedOn) {
        this.utcUpdatedOn = utcUpdatedOn;
    }
    public Date getUtcCreatedOn() {
        return utcCreatedOn;
    }
    public void setUtcCreatedOn(Date utcCreatedOn) {
        this.utcCreatedOn = utcCreatedOn;
    }
    @Override
    public Map<String,String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("content", getContent());
        return params;
    }
    @Override
    public String buildUrl(GitLabRepository glr) {
        return GitLabRepository.API_GITLAB + "/projects/" + issue.getProjectId() + "/issues/" + issue.getId() + "/notes/";
    }
    @Override
    public String getKey() {
        return getCommentId();
    }
    @Override
    public Type getListType() {
        return new TypeToken<List<GitLabNote>>(){}.getType();
    }
    public GitLabIssue getIssue() {
        return issue;
    }
    public void setIssue(GitLabIssue issue) {
        this.issue = issue;
    }
    public GitLabUser getAuthorInfo() {
        return authorInfo;
    }
    public void setAuthorInfo(GitLabUser authorInfo) {
        this.authorInfo = authorInfo;
    }
    
}
