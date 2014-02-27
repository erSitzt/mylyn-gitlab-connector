package com.ersitzt.gitlab.core.models;

import java.lang.reflect.Type;

import com.ersitzt.gitlab.core.GitLabRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



public class GitLabIssues implements GitLabModelI {

	private final List<GitLabIssue> issues = new ArrayList<GitLabIssue>();
	
    private int count = 0;
    
    public int getCount() {
        return count;
    }

    public GitLabIssue get(int idx) {
        return issues.get(idx);
    }
    
    public List<GitLabIssue> getIssues() {
        return issues;
    }
    
    public int addMoreIssues(List<GitLabIssue> moreIssues) {
        issues.addAll(moreIssues);
        return issues.size();
    }
    
	@Override
	public Map<String, String> getParams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("GitLabIssues[")
           .append("count=").append(count);
        if (!issues.isEmpty()) {
            Iterator<GitLabIssue> iter = issues.iterator();
            str.append(", issues=[")
               .append(iter.next().getId());
            while (iter.hasNext()) {
                str.append(",")
                   .append(iter.next().getId());
            }
            str.append("]");
        }
        str.append("]");
        return str.toString();
	}

	@Override
	public String buildUrl(GitLabRepository glr) {
        return GitLabRepository.API_GITLAB + 
                "issues" + "/";
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public Type getListType() {
		// TODO Auto-generated method stub
		return null;
	}

}
