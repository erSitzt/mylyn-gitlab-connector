package com.ersitzt.gitlab.core;

public class GitLabServiceException extends Exception {

    private static final long serialVersionUID = -1;

    /**
     * Constructor.
     * @param cause
     */
    public GitLabServiceException(Throwable cause) {
        super(cause);
    }

    public GitLabServiceException(String message) {
        super(message);
    }

    public GitLabServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
