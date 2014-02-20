package com.ersitzt.gitlab.core;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.mylyn.tasks.core.AbstractRepositoryConnector;
import org.eclipse.mylyn.tasks.core.IRepositoryQuery;
import org.eclipse.mylyn.tasks.core.ITask;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.TaskData;
import org.eclipse.mylyn.tasks.core.data.TaskDataCollector;
import org.eclipse.mylyn.tasks.core.sync.ISynchronizationSession;

public class GitLabRepositoryConnector extends AbstractRepositoryConnector {
//test
	@Override
	public boolean canCreateNewTask(@NonNull TaskRepository repository) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canCreateTaskFromKey(@NonNull TaskRepository repository) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@NonNull
	public String getConnectorKind() {
		// TODO Auto-generated method stub
		return GitLabCorePlugin.CONNECTOR_KIND;
	}

	@Override
	@NonNull
	public String getLabel() {
		// TODO Auto-generated method stub
		return "GitLab Repository Connector";
	}

	@Override
	@Nullable
	public String getRepositoryUrlFromTaskUrl(@NonNull String taskUrl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@NonNull
	public TaskData getTaskData(@NonNull TaskRepository repository,
			@NonNull String taskIdOrKey, @NonNull IProgressMonitor monitor)
			throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Nullable
	public String getTaskIdFromTaskUrl(@NonNull String taskUrl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Nullable
	public String getTaskUrl(@NonNull String repositoryUrl,
			@NonNull String taskIdOrKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasTaskChanged(@NonNull TaskRepository taskRepository,
			@NonNull ITask task, @NonNull TaskData taskData) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@NonNull
	public IStatus performQuery(@NonNull TaskRepository repository,
			@NonNull IRepositoryQuery query,
			@NonNull TaskDataCollector collector,
			@Nullable ISynchronizationSession session,
			@NonNull IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateRepositoryConfiguration(
			@NonNull TaskRepository taskRepository,
			@NonNull IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTaskFromTaskData(@NonNull TaskRepository taskRepository,
			@NonNull ITask task, @NonNull TaskData taskData) {
		// TODO Auto-generated method stub
		
	}

}
