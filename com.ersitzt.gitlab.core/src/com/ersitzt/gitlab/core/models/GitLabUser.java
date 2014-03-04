package com.ersitzt.gitlab.core.models;

import java.lang.reflect.Type;
import java.util.Map;

import com.ersitzt.gitlab.core.GitLabRepository;

public class GitLabUser implements GitLabModelI {
	
	private Integer id;
	private String username;
	private String email;
	private String name;
	private String state;
	private String created_at;

	public Integer getId() {
	return id;
	}

	public void setId(Integer id) {
	this.id = id;
	}

	public String getUsername() {
	return username;
	}

	public void setUsername(String username) {
	this.username = username;
	}

	public String getEmail() {
	return email;
	}

	public void setEmail(String email) {
	this.email = email;
	}

	public String getName() {
	return name;
	}

	public void setName(String name) {
	this.name = name;
	}

	public String getState() {
	return state;
	}

	public void setState(String state) {
	this.state = state;
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
		return GitLabRepository.API_GITLAB + "/users/" + this.id;
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
