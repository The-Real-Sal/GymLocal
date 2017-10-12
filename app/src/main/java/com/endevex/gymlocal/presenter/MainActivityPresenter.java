package com.endevex.gymlocal.presenter;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.endevex.gymlocal.R;
import com.endevex.gymlocal.model.User;
import com.endevex.gymlocal.utils.Constants;
import com.endevex.gymlocal.utils.EmailValidator;
import com.endevex.gymlocal.view.MainView;

import java.util.List;

/**
 * Created by Sal on 6/10/17.
 */
public class MainActivityPresenter {
    private MainView mMainView;
    private SharedPreferences mSp;

    public MainActivityPresenter(MainView view, SharedPreferences sp) {
        mMainView = view;
        mSp = sp;
    }

    public void checkLoggedIn() {
        String email = mSp.getString(Constants.LOGGED_IN_USER_EMAIL, "");
        if (email.equals("")) {
            mMainView.navigateToLogin();
        } else {
        }
    }


}
