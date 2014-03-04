package com.ersitzt.gitlab.core.models;

import java.lang.reflect.Type;
import java.util.Map;

import com.ersitzt.gitlab.core.GitLabRepository;

public class GitLabUser implements GitLabModelI {
	
	private Integer id;
	private String username;
	private String email;
	private String name;
	private String private_token;
	private String state;
	private String created_at;
	private Object bio;
	private String skype;
	private String linkedin;
	private String twitter;
	private String website_url;
	private String extern_uid;
	private String provider;
	private Integer theme_id;
	private Integer color_scheme_id;
	private Boolean is_admin;
	private Boolean can_create_group;
	private Boolean can_create_project;

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
	
	public String getPrivate_token() {
		return private_token;
	}

	public void setPrivate_token(String private_token) {
		this.private_token = private_token;
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

	public Object getBio() {
	return bio;
	}

	public void setBio(Object bio) {
	this.bio = bio;
	}

	public String getSkype() {
	return skype;
	}

	public void setSkype(String skype) {
	this.skype = skype;
	}

	public String getLinkedin() {
	return linkedin;
	}

	public void setLinkedin(String linkedin) {
	this.linkedin = linkedin;
	}

	public String getTwitter() {
	return twitter;
	}

	public void setTwitter(String twitter) {
	this.twitter = twitter;
	}

	public String getWebsite_url() {
	return website_url;
	}

	public void setWebsite_url(String website_url) {
	this.website_url = website_url;
	}

	public String getExtern_uid() {
	return extern_uid;
	}

	public void setExtern_uid(String extern_uid) {
	this.extern_uid = extern_uid;
	}

	public String getProvider() {
	return provider;
	}

	public void setProvider(String provider) {
	this.provider = provider;
	}

	public Integer getTheme_id() {
	return theme_id;
	}

	public void setTheme_id(Integer theme_id) {
	this.theme_id = theme_id;
	}

	public Integer getColor_scheme_id() {
	return color_scheme_id;
	}

	public void setColor_scheme_id(Integer color_scheme_id) {
	this.color_scheme_id = color_scheme_id;
	}

	public Boolean getIs_admin() {
	return is_admin;
	}

	public void setIs_admin(Boolean is_admin) {
	this.is_admin = is_admin;
	}

	public Boolean getCan_create_group() {
	return can_create_group;
	}

	public void setCan_create_group(Boolean can_create_group) {
	this.can_create_group = can_create_group;
	}

	public Boolean getCan_create_project() {
	return can_create_project;
	}

	public void setCan_create_project(Boolean can_create_project) {
	this.can_create_project = can_create_project;
	}


	@Override
	public Map<String, String> getParams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String buildUrl(GitLabRepository glr) {
		// TODO Auto-generated method stub
		return GitLabRepository.API_GITLAB + "/user/";
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
