package com.endevex.gymlocal.presenter;


import com.endevex.gymlocal.model.User;
import com.endevex.gymlocal.utils.EmailValidator;
import com.endevex.gymlocal.view.SignUpView;

import java.util.List;

/**
 *
 * Created by Leivant on 6/10/2017.
 */

public class SignUpActivityPresenter {

    private SignUpView mSignUpView;

    /**
     * Constructor for register presenter
     *
     * @param view the view of RegisterActivity
     */
    public SignUpActivityPresenter(SignUpView view) {
        mSignUpView = view;
    }

    public void registerUser(String firstName, String lastName, String email, String password,
                             String passwordRetyped, boolean gymOwner) {
        if (validate(firstName, lastName, email, password, passwordRetyped)) {
            boolean userType = gymOwner;
            User user = new User(firstName, lastName, email, password, userType);
            user.save();
            mSignUpView.returnLoginUser(email, password);
        }
    }

    private boolean validate(String firstName, String lastName, String email, String password, String passwordRetyped) {
        if (firstName.isEmpty()) {
            mSignUpView.showErrorFirstName();
            return false;
        }
        if (lastName.isEmpty()) {
            mSignUpView.showErrorLastName();
            return false;
        }
        if (email.isEmpty()) {
            mSignUpView.showErrorEmail();
            return false;
        }
        if (!isEmail(email)) {
            mSignUpView.showErrorValidEmail();
            return false;
        }
        if (emailExists(email)) {
            mSignUpView.showErrorEmailExists();
            return false;
        }
        if (password.isEmpty() || passwordRetyped.isEmpty()) {
            mSignUpView.showErrorPassword();
            return false;
        }
        if (!password.equals(passwordRetyped)) {
            mSignUpView.showErrorPasswordMatch();
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
