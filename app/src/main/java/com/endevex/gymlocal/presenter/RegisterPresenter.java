package com.endevex.gymlocal.presenter;


import android.content.SharedPreferences;
import android.widget.ShareActionProvider;

import com.endevex.gymlocal.model.User;
import com.endevex.gymlocal.utils.Constants;
import com.endevex.gymlocal.utils.EmailValidator;
import com.endevex.gymlocal.view.RegisterView;

import java.util.List;

/**
 * Created by Leivant on 6/10/2017.
 */

public class RegisterPresenter {

    private SharedPreferences mSp;
    private RegisterView mRegisterView;

    /**
     * Constructor for register presenter
     *
     * @param view the view of RegisterActivity
     */
    public RegisterPresenter(RegisterView view, SharedPreferences sp) {
        mRegisterView = view;
        mSp = sp;
    }

    public void registerUser(String firstName, String lastName, String email, String password, String passwordRetyped) {
        if (validate(firstName, lastName, email, password, passwordRetyped)) {
            User user = new User(firstName, lastName, email, password);
            user.save();
            SharedPreferences.Editor editor = mSp.edit();
            editor.putString(Constants.LOGGED_IN_USER_EMAIL, email);
            editor.apply();
            mRegisterView.returnLoginUser(email, password);
        }
    }

    private boolean validate(String firstName, String lastName, String email, String password, String passwordRetyped) {
        if (firstName.isEmpty()) {
            mRegisterView.showErrorFirstName();
            return false;
        }
        if (lastName.isEmpty()) {
            mRegisterView.showErrorLastName();
            return false;
        }
        if (email.isEmpty()) {
            mRegisterView.showErrorEmail();
            return false;
        }
        if (!isEmail(email)) {
            mRegisterView.showErrorValidEmail();
            return false;
        }
        if (emailExists(email)) {
            mRegisterView.showErrorEmailExists();
            return false;
        }
        if (password.isEmpty() || passwordRetyped.isEmpty()) {
            mRegisterView.showErrorPassword();
            return false;
        }
        if (!password.equals(passwordRetyped)) {
            mRegisterView.showErrorPasswordMatch();
            return false;
        }
        return true;
    }

    private boolean isEmail(String str) {
        EmailValidator ev = new EmailValidator();
        return ev.validate(str);
    }

    private boolean emailExists(String email) {
        List<User> userList = User.find(User.class, "M_EMAIL = ? ",email);
        return userList.size() > 0;
    }

}
