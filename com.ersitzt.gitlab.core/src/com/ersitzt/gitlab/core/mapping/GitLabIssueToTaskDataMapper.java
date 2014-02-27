package com.ersitzt.gitlab.core.mapping;

import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.TaskData;

import com.ersitzt.gitlab.core.models.GitLabIssue;

public interface GitLabIssueToTaskDataMapper {
    /**
     * populates the provided TaskData with empty attributes - creating a new Issue Editor. 
     */
    public abstract void addAttributesToTaskData(TaskData data,TaskRepository repository);
    /**
     * populates the TaskData with attributes taken from the BBIssue object provided
     */
    public abstract void applyToTaskData(GitLabIssue issue, TaskData data, TaskRepository repository);
    /**
     * populates the provided BBIssue from the TaskData. 
     */
    public abstract void applyToIssue(TaskData data, GitLabIssue issue);
    
    /**
     * Check whether the TaskData isValid to update in Bitbucket in regards to the section this mapper handles.
     */
    public abstract boolean isValid(TaskData data);
}
