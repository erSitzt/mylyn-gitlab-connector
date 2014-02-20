package com.ersitzt.gitlab.ui.wizard;

import org.eclipse.mylyn.tasks.core.RepositoryTemplate;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.ui.wizards.AbstractRepositorySettingsPage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.ersitzt.gitlab.core.GitLabCorePlugin;

public class GitLabRepositorySettingsPage extends
		AbstractRepositorySettingsPage {

	public GitLabRepositorySettingsPage(TaskRepository repository) {
		super("GitLab Repository Settings", "Settings for GitLab Repository", repository);
		setNeedsAnonymousLogin(false);
		setNeedsEncoding(false);
		setNeedsTimeZone(false);
		setNeedsAdvanced(false);
		setNeedsProxy(false);
		
	}
	
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		addRepositoryTemplatesToServerUrlCombo(); //important
	}
	
	@Override
	protected void repositoryTemplateSelected(RepositoryTemplate template) {
		repositoryLabelEditor.setStringValue(template.label);
		setUrl(template.repositoryUrl);
		setUserId("user");
		setPassword("pass");
		
		getContainer().updateButtons();
	}
	
	@Override
	public String getConnectorKind() {
		// TODO Auto-generated method stub
		return GitLabCorePlugin.CONNECTOR_KIND;
	}

	@Override
	protected void createAdditionalControls(Composite parent) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Validator getValidator(TaskRepository repository) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected boolean isValidUrl(String url) {
		// TODO Auto-generated method stub
		return url != null && url.trim().length() > 0 ;
	}

}
