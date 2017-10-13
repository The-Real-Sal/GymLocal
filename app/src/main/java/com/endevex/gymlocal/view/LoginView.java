package com.endevex.gymlocal.view;

/**
 * View for Login. As app is using MVP Architecture.
 * Created by Leivant on 13/10/2017.
 */

public interface LoginView {

    void showErrorMessage();

    void showLoginSuccess(String email);

    void showLoginFailure();
}
