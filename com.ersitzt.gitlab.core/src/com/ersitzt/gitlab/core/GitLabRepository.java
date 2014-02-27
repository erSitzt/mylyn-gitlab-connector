package com.ersitzt.gitlab.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitLabRepository {
	public static final String API_GITLAB = "/api/v3";
	private String repourl;
	private String apitoken;
	

	public GitLabRepository(String repourl, String apitoken) {
		this.repourl = repourl;
		this.setApitoken(apitoken);
	}

	public String getIssueUrl(String id) {
		return repourl + "/" + "issues" + "/" + id;
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

}
