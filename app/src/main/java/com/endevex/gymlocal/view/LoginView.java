package com.endevex.gymlocal.view;

/**
 * View for Login. As app is using MVP Architecture.
 * Created by Leivant on 13/10/2017.
 */
public interface LoginView {

    /**
     * Display error message for non valid entry
     */
    void showErrorMessage();

    /**
     * Show login success if authentication.
     * @param email
     */
    void showLoginSuccess(String email);

    /**
     * Display login failure due to incorrect credentials.
     */
    void showLoginFailure();
}
