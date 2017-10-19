package com.endevex.gymlocal.presenter;

import android.content.SharedPreferences;

import com.endevex.gymlocal.model.User;
import com.endevex.gymlocal.utils.Constants;
import com.endevex.gymlocal.utils.EmailValidator;
import com.endevex.gymlocal.view.LoginView;

import java.util.List;

/**
 * Activity's presenter. Where all the Logic gets done for the Login Activity.
 * Created by Leivant on 13/10/2017.
 */
public class LoginActivityPresenter {

    private LoginView mLoginView;

    /**
     * Construct to attach view and presenter.
     * @param view
     */
    public LoginActivityPresenter(LoginView view) {
        mLoginView = view;
    }

    /**
     * Checks email and password are valid and attempts to login if so.
     * @param email
     * @param password
     */
    public void login(String email, String password) {
        if (validate(email, password)) {
            if (validateLogin(email, password)) {
                mLoginView.showLoginSuccess(email);
            } else {
                mLoginView.showLoginFailure();
            }
        } else {
            mLoginView.showErrorMessage();
        }

    }

    /**
     * Validates if credentials are correct
     * @param email
     * @param password
     * @return
     */
    private boolean validateLogin(String email, String password) {
        List<User> userList = User.find(User.class, "M_EMAIL = ? and M_PASSWORD = ? ", email, password);
        return userList.size() == 1;
    }

    /**
     * Validates email is an email and password is not empty.
     * @param i
     * @return
     */
    private boolean validate(String... i) {
        int counter = 1;
        for (String str : i) {
            if (str.isEmpty()) return false;
            if (counter < i.length) {
                if (!isEmail(str)) return false;
            }
            counter++;
        }
        return true;
    }

    /**
     * Checks email is valid using regex from EmailValidator class
     * @param str
     * @return true or false
     */
    private boolean isEmail(String str) {
        EmailValidator ev = new EmailValidator();
        return ev.validate(str);
    }

}
