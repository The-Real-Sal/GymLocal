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

    public boolean validate(String... i) {
        int counter = 1;
        for (String str : i) {
            if (isEmpty(str)) return false;
            if (counter < i.length) {
                if (!isEmail(str)) return false;
            }
            counter++;
        }
        return true;
    }

    private boolean isEmail(String str) {
        EmailValidator ev = new EmailValidator();
        if (ev.validate(str)) return true;
        return false;
    }

    public boolean isEmpty(String str) {
        if (str.length() == 0) return true;
        return false;
    }


}
