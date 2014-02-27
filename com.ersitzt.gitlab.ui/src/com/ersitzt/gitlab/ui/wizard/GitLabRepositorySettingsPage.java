package com.ersitzt.gitlab.ui.wizard;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.mylyn.tasks.core.RepositoryTemplate;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.ui.wizards.AbstractRepositorySettingsPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.ersitzt.gitlab.core.GitLabCorePlugin;
import com.ersitzt.gitlab.core.GitLabRepository;

public class GitLabRepositorySettingsPage extends
		AbstractRepositorySettingsPage {
	private String apitoken;
	private Label apiKeyLabel;
	private Text apiKeyText;
	private Button apiKeyEnableButton;
	private String checkedUrl;

	@Override
	public void applyTo(@NonNull TaskRepository repository) {
		super.applyTo(repository);
		if(useApiKey()) {
		repository.setProperty("API_KEY", apiKeyText.getText().trim());
		} else {
		repository.removeProperty("API_KEY");
		}
	}

	public GitLabRepositorySettingsPage(TaskRepository repository) {
		super("GitLab Repository Settings", "Settings for GitLab Repository",
				repository);
		setNeedsAnonymousLogin(false);
		setNeedsEncoding(false);
		setNeedsTimeZone(false);
		setNeedsAdvanced(true);
		setNeedsProxy(true);

	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		addRepositoryTemplatesToServerUrlCombo(); // important
		repository.getUrl();
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
	protected void createSettingControls(Composite parent) {
		// TODO Auto-generated method stub
		super.createSettingControls(parent);

		// oldApiKey
		String apiKey = null;
		boolean useApiKey = apiKey != null && !apiKey.isEmpty();

		// REPOSITORY_SETTING_API_KEY
		apiKeyLabel = new Label(parent, SWT.NONE);
		apiKeyLabel.setText("ApiKey");

		apiKeyText = new Text(parent, SWT.BORDER);
		apiKeyText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		apiKeyText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				isPageComplete();

			}
		});

		if (apiKey != null) {
			apiKeyText.setText(apiKey);
		}

		apiKeyEnableButton = new Button(parent, SWT.CHECK);
		apiKeyEnableButton.setText("Enable");
		apiKeyEnableButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setApiKeyUsage(apiKeyEnableButton.getSelection());
				isPageComplete();
			}
		});

		apiKeyLabel.moveBelow(savePasswordButton);
		apiKeyText.moveBelow(apiKeyLabel);
		apiKeyEnableButton.moveBelow(apiKeyText);

		setApiKeyUsage(useApiKey);
	}

	@Override
	protected void createAdditionalControls(Composite parent) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Validator getValidator(final TaskRepository repository) {
		return new Validator() {
			@Override
			public void run(IProgressMonitor monitor) throws CoreException {
				if (!isValidUrl(repository.getUrl())) {
					throw new CoreException(new Status(IStatus.ERROR,
							GitLabCorePlugin.CONNECTOR_KIND, "Falsche URL"));
				}
				checkedUrl = repository.getRepositoryUrl();
			}

		};
	}

	@Override
	public boolean isPageComplete() {
		String errorMessage = null;

		if (isMissingApiKey()) {
			errorMessage = "Kein API Key";
		}

		if (errorMessage != null) {
			setMessage(errorMessage, IMessageProvider.ERROR);
			return false;
		} else {
			return super.isPageComplete() && checkedUrl != null
					&& checkedUrl.equals(getRepositoryUrl());
		}
	}

	@Override
	protected boolean isValidUrl(String url) {
		return url.matches("^http?://.+"); //$NON-NLS-1$
	}

	@Override
	protected boolean isMissingCredentials() {
		return !useApiKey() && super.isMissingCredentials();
	}

	private boolean isMissingApiKey() {
		return useApiKey() && apiKeyText.getText().trim().isEmpty();
	}

	private boolean useApiKey() {
		return apiKeyEnableButton != null && apiKeyEnableButton.getSelection();
	}

	protected boolean isMissingApiKeyUsage() {
		try {
			return !useApiKey() && getHttpAuth();
		} catch (NullPointerException e) {
			return false;
		}
	}

	private void setApiKeyUsage(boolean use) {
		Composite parent = apiKeyEnableButton.getParent();

		repositoryUserNameEditor.setEnabled(!use, parent);
		repositoryPasswordEditor.setEnabled(!use, parent);

		apiKeyEnableButton.setSelection(use);
		apiKeyText.setEnabled(use);

	}

}
