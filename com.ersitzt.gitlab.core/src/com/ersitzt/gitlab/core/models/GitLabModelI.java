package com.ersitzt.gitlab.core.models;

import java.lang.reflect.Type;
import java.util.Map;

import com.ersitzt.gitlab.core.GitLabRepository;

public interface GitLabModelI {
    public Map<String, String> getParams();
    
    public String buildUrl(GitLabRepository glr);
    
    public String getKey();
    
    public Type getListType();

}
