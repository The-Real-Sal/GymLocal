package com.endevex.gymlocal.view;

/**
 * This is the main activity view.
 * Created by Sal on 6/10/17.
 */
public interface MainView {
    /**
     * Send unauthorised users / no login users to the login screen.
     */
    void navigateToLogin();

    /**
     * Log in a confirmed user and display their name.
     * @param name
     */
    void logUserIn(String name);
}
