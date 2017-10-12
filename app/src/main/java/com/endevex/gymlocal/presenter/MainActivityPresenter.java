package com.endevex.gymlocal.presenter;

import com.endevex.gymlocal.R;
import com.endevex.gymlocal.utils.EmailValidator;
import com.endevex.gymlocal.view.MainView;

/**
 * Created by Sal on 6/10/17.
 */
public class MainActivityPresenter {
    private MainView mView;
    public MainActivityPresenter(MainView view) {
        mView = view;
    }

    public void login(String email, String password) {
        boolean result = validate(email,password);
        if(!result) mView.showErrorMessage();
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
