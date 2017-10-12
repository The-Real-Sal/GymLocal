package com.endevex.gymlocal.presenter;

import android.content.SharedPreferences;

import com.endevex.gymlocal.model.User;
import com.endevex.gymlocal.utils.Constants;
import com.endevex.gymlocal.utils.EmailValidator;
import com.endevex.gymlocal.view.LoginView;

import java.util.List;

/**
 * Created by Leivant on 13/10/2017.
 */

public class LoginActivityPresenter {

    private LoginView mLoginView;
    private SharedPreferences mSp;

    public LoginActivityPresenter(LoginView view, SharedPreferences sp) {
        mLoginView = view;
        mSp=sp;
    }

    public void login(String email, String password) {
        if (validate(email, password)) {
            if (validateLogin(email, password)) {
                saveUserToPreferences(email);
                mLoginView.showLoginSuccess();
            } else {
                mLoginView.showLoginFailure();
            }
        } else {
            mLoginView.showErrorMessage();
        }

    }

    private void saveUserToPreferences(String email) {
        SharedPreferences.Editor editor = mSp.edit();
        editor.putString(Constants.LOGGED_IN_USER_EMAIL, email);
        editor.apply();
    }

    private boolean validateLogin(String email, String password) {
        List<User> userList = User.find(User.class, "M_EMAIL = ? and M_PASSWORD = ? ", email, password);
        return userList.size() == 1;
    }

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

    private boolean isEmail(String str) {
        EmailValidator ev = new EmailValidator();
        return ev.validate(str);
    }

}
