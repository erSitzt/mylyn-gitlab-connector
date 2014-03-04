package com.ersitzt.gitlab.core.models;

import java.lang.reflect.Type;
import java.util.Map;

import com.ersitzt.gitlab.core.GitLabRepository;

public class GitLabMilestone implements GitLabModelI {

	private Integer id;
	private Integer iid;
	private Integer project_id;
	private String title;
	private String description;
	private String due_date;
	private String state;
	private String updated_at;
	private String created_at;


	public Integer getId() {
	return id;
	}

	public void setId(Integer id) {
	this.id = id;
	}

	public Integer getIid() {
	return iid;
	}

	public void setIid(Integer iid) {
	this.iid = iid;
	}

	public Integer getProject_id() {
	return project_id;
	}

	public void setProject_id(Integer project_id) {
	this.project_id = project_id;
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

	public String getDue_date() {
	return due_date;
	}

	public void setDue_date(String due_date) {
	this.due_date = due_date;
	}

	public String getState() {
	return state;
	}

	public void setState(String state) {
	this.state = state;
	}

	public String getUpdated_at() {
	return updated_at;
	}

	public void setUpdated_at(String updated_at) {
	this.updated_at = updated_at;
	}

	public String getCreated_at() {
	return created_at;
	}

	public void setCreated_at(String created_at) {
	this.created_at = created_at;
	}



	@Override
	public Map<String, String> getParams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String buildUrl(GitLabRepository glr) {
		// TODO Auto-generated method stub
		return GitLabRepository.API_GITLAB + "/projects/" + this.project_id + "/milestones/" + this.id;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type getListType() {
		// TODO Auto-generated method stub
		return null;
	}

}
