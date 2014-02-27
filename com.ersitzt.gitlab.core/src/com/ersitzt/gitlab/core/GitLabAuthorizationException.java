package com.ersitzt.gitlab.core;

public class GitLabAuthorizationException extends GitLabServiceException {

    /**
     * Used for http status codes 401 and 403, to facilitate 
     * recognition of username/password absence.
     */
    private static final long serialVersionUID = 1L;

    public GitLabAuthorizationException(Throwable cause) {
        super(cause);
    }

    public GitLabAuthorizationException(String message) {
        super(message);
    }

    public GitLabAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
