package com.ersitzt.gitlab.core;

import org.eclipse.mylyn.commons.net.AuthenticationCredentials;
import org.eclipse.mylyn.commons.net.AuthenticationType;
import org.eclipse.mylyn.tasks.core.TaskRepository;

public class GitLabCredentials {
    public static GitLabCredentials create(TaskRepository repository) {
        return new GitLabCredentials(repository.getCredentials(AuthenticationType.REPOSITORY));
    }

    private final String username;
    private final String password;

    public GitLabCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public GitLabCredentials(AuthenticationCredentials credentials) {
        this(credentials.getUserName(), credentials.getPassword());
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "GitLabCredentials[username=" + username + ", password=" + password + "]";
    }
}
