package com.ersitzt.gitlab.ui;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.mylyn.tasks.core.IRepositoryQuery;
import org.eclipse.mylyn.tasks.core.ITaskMapping;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.ui.AbstractRepositoryConnectorUi;
import org.eclipse.mylyn.tasks.ui.wizards.ITaskRepositoryPage;
import org.eclipse.mylyn.tasks.ui.wizards.NewTaskWizard;

import com.ersitzt.gitlab.core.GitLabCorePlugin;
import com.ersitzt.gitlab.ui.wizard.GitLabRepositorySettingsPage;

public class GitLabConnectorUi extends AbstractRepositoryConnectorUi {

	public GitLabConnectorUi() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getConnectorKind() {
		// TODO Auto-generated method stub
		return GitLabCorePlugin.CONNECTOR_KIND;
	}

	@Override
	public ITaskRepositoryPage getSettingsPage(TaskRepository repository) {
		// TODO Auto-generated method stub
		return new GitLabRepositorySettingsPage(repository);
	}

	@Override
	public IWizard getQueryWizard(TaskRepository repository,
			IRepositoryQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IWizard getNewTaskWizard(TaskRepository repository,
			ITaskMapping selection) {
		// TODO Auto-generated method stub
		return new NewTaskWizard(repository, selection);
	}

	@Override
	public boolean hasSearchPage() {
		// TODO Auto-generated method stub
		return false;
	}

}
