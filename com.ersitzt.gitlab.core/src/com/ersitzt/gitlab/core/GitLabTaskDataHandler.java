package com.ersitzt.gitlab.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.mylyn.tasks.core.ITaskMapping;
import org.eclipse.mylyn.tasks.core.RepositoryResponse;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.AbstractTaskDataHandler;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.core.data.TaskAttributeMapper;
import org.eclipse.mylyn.tasks.core.data.TaskData;

import com.ersitzt.gitlab.core.mapping.GitLabIssueToTaskDataMapper;
import com.ersitzt.gitlab.core.models.GitLabIssue;

public class GitLabTaskDataHandler extends AbstractTaskDataHandler {
	private static final String DATA_VERSION = "1";
	private List<GitLabIssueToTaskDataMapper> mappers = new ArrayList<GitLabIssueToTaskDataMapper>();
	
	@Override
	public RepositoryResponse postTaskData(@NonNull TaskRepository repository,
			@NonNull TaskData taskData,
			@Nullable Set<TaskAttribute> oldAttributes,
			@Nullable IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean initializeTaskData(@NonNull TaskRepository repository,
			@NonNull TaskData data, @Nullable ITaskMapping initializationData,
			@Nullable IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TaskAttributeMapper getAttributeMapper(
			@NonNull TaskRepository repository) {
		// TODO Auto-generated method stub
		return new TaskAttributeMapper(repository);
	}
    // This populates from the server
    public TaskData toTaskData(TaskRepository repository, GitLabIssue issue) throws GitLabServiceException {
        
        TaskData data = new TaskData(getAttributeMapper(repository), GitLabRepositoryConnector.KIND,
                repository.getRepositoryUrl(), issue.getId());
        data.setVersion(DATA_VERSION);
        for (GitLabIssueToTaskDataMapper mapper : mappers) {
            mapper.applyToTaskData(issue, data, repository);
        }
        addNewCommentSection(data);
        data.setPartial(hasNullValueInRequiredAttributes(data));
        
        return data;
    }
    private void addNewCommentSection(TaskData data) {

        data.getRoot().createAttribute(TaskAttribute.COMMENT_NEW).getMetaData()
            .setType(TaskAttribute.TYPE_LONG_RICH_TEXT).setReadOnly(false);        
    }
    
    // return true if data has null value of required attribute 
    private boolean hasNullValueInRequiredAttributes(TaskData data) {
        for (GitLabIssueToTaskDataMapper mapper : mappers) {
            if (!mapper.isValid(data)) return true;
        }
        return false;
    }

}
