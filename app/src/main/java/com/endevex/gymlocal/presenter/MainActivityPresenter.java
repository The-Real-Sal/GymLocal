package com.endevex.gymlocal.presenter;

import com.endevex.gymlocal.R;
import com.endevex.gymlocal.model.User;
import com.endevex.gymlocal.utils.EmailValidator;
import com.endevex.gymlocal.view.MainView;

import java.util.List;

/**
 * Created by Sal on 6/10/17.
 */
public class MainActivityPresenter {
    private MainView mView;

    public MainActivityPresenter(MainView view) {
        mView = view;
    }

    public void login(String email, String password) {
        if (validate(email, password)) {
            if (validateLogin(email, password)) {
                // TODO: 13/10/2017 Set User in Preferences
                mView.showLoginSuccess();
            } else {
                mView.showLoginFailure();
            }
        } else {
            mView.showErrorMessage();
        }

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
