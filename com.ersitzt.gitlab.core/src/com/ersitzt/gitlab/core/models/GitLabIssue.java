package com.ersitzt.gitlab.core.models;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map;

import com.ersitzt.gitlab.core.GitLabRepository;
import com.ersitzt.gitlab.core.GitLabRepository;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.reflect.TypeToken;

public class GitLabIssue implements GitLabModelI {
	
	private String id;
	private String iid;
	private String projectId;
	private String title;
	private String description;
	private String[] labels;
	private String milestone;
	private String assignee;
	private GitLabAuthor author;
	private String state;
	private Date updatedAt;
	private Date createdAt;

	public GitLabIssue()
	{
		
	}
	public GitLabIssue(String title, String description, String state)
	{
		this.title = title;
		this.description = description;
		this.state = state;
	}
	@Override
	public Map<String, String> getParams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String buildUrl(GitLabRepository glr) {
        return GitLabRepository.API_GITLAB + 
                "issues" + 
                "?private_token=" + glr.getApitoken();
	}

	@Override
	public String getKey() {
		return this.id;
	}

	@Override
	public Type getListType() {
		return new TypeToken<List<GitLabIssue>>(){}.getType();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIid() {
		return iid;
	}

	public void setIid(String iid) {
		this.iid = iid;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String[] getLabels() {
		return labels;
	}

	public void setLabels(String[] labels) {
		this.labels = labels;
	}

	public String getMilestone() {
		return milestone;
	}

	public void setMilestone(String milestone) {
		this.milestone = milestone;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public GitLabAuthor getAuthor() {
		return author;
	}

	public void setAuthor(GitLabAuthor author) {
		this.author = author;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
