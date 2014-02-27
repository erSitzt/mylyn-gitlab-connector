package com.ersitzt.gitlab.core;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class GitLabStatus extends Status {
    public GitLabStatus(int severity, String message, Throwable exception) {
        super(severity, GitLab.BUNDLE_ID, message, exception);
    }

    public GitLabStatus(int severity, String message) {
        super(severity, GitLab.BUNDLE_ID, message);
    }

    public static IStatus newErrorStatus(String message) {
        return new GitLabStatus(IStatus.ERROR, message);
    }

    public static IStatus newErrorStatus(Throwable t) {
        return new GitLabStatus(IStatus.ERROR, t.getMessage(), t);
    }
    
    public static IStatus newErrorStatus(String message, Throwable t) {
        return new GitLabStatus(IStatus.ERROR, message, t);
    }

}
