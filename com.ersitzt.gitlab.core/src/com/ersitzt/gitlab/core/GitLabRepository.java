package com.ersitzt.gitlab.core;

public class GitLabRepository {
	public static final String API_GITLAB = "/api/v3";
	private String repourl;
	private String apitoken;
	
	public GitLabRepository(String repourl, String apitoken)
	{
		this.repourl = repourl;
		this.setApitoken(apitoken);
	}
	
	public String getIssueUrl(String id)
	{
		return repourl + "/" +
				"issues" + "/" +
				id;
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

	

}
