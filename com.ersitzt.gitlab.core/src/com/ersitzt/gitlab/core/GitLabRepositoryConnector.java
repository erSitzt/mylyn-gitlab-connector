package com.ersitzt.gitlab.core;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.mylyn.tasks.core.AbstractRepositoryConnector;
import org.eclipse.mylyn.tasks.core.IRepositoryQuery;
import org.eclipse.mylyn.tasks.core.ITask;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.TaskData;
import org.eclipse.mylyn.tasks.core.data.TaskDataCollector;
import org.eclipse.mylyn.tasks.core.sync.ISynchronizationSession;

import com.ersitzt.gitlab.core.models.GitLabNote;
import com.ersitzt.gitlab.core.models.GitLabIssue;
import com.ersitzt.gitlab.core.models.GitLabIssues;

public class GitLabRepositoryConnector extends AbstractRepositoryConnector {
    /** GitLab kind. */
    protected static final String KIND = GitLab.CONNECTOR_KIND;
    /** GitLab specific {@link AbstractTaskDataHandler}. */
    private final GitLabTaskDataHandler taskDataHandler;

    /**
     * Constructor for BitbucketRepositoryConnector.
     */
    public GitLabRepositoryConnector() {
        this.taskDataHandler = new GitLabTaskDataHandler();
    }

	@Override
	public boolean canCreateNewTask(@NonNull TaskRepository repository) {
		// TODO Auto-generated method stub
		return true;
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
		monitor.beginTask("Querying repository ...", 1);
		try {
			GitLabIssues issues = GitLabService.get(repository).searchIssues(
					GitLabQuery.get(query));
			// collect task data
			for (GitLabIssue issue : issues.getIssues()) {
				addCommentsToIssue(repository, issue);
				TaskData taskData = taskDataHandler.toTaskData(repository,
						issue);
				collector.accept(taskData);
			}
			return Status.OK_STATUS;
		} catch (GitLabServiceException e) {
			return GitLabStatus.newErrorStatus(e);
		} finally {
			monitor.done();
		}
	}
    public void addCommentsToIssue(TaskRepository repository,GitLabIssue issue) throws GitLabServiceException {        
        List<GitLabNote> comments = GitLabService.get(repository).doGetList(new GitLabNote(issue));
        for (GitLabNote comment : comments) {
            comment.setIssue(issue);
        }
        Collections.sort(comments,new Comparator<GitLabNote>() {
            @Override
            public int compare(GitLabNote o1, GitLabNote o2) {
                return o1.getUtcCreatedOn().compareTo(o2.getUtcCreatedOn());
            }
        });
        //issue.setComments(comments.toArray(new GitLabComment[comments.size()]));
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
