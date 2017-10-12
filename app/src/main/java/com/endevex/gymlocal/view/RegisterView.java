package com.endevex.gymlocal.view;

/**
 * Created by Sal on 6/10/17.
 */

public interface RegisterView {
    void showErrorFirstName();
    void showErrorLastName();
    void showErrorEmail();
    void showErrorValidEmail();
    void showErrorPassword();
    void showErrorPasswordMatch();
    void returnLoginUser(String email, String password);
}
