package com.endevex.gymlocal.presenter;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.endevex.gymlocal.R;
import com.endevex.gymlocal.model.User;
import com.endevex.gymlocal.utils.Constants;
import com.endevex.gymlocal.utils.EmailValidator;
import com.endevex.gymlocal.view.MainView;

import java.util.List;

/**
 * Activity's presenter. Where all the Logic gets done for the Main Activity.
 * Created by Sal on 6/10/17.
 */
public class MainActivityPresenter {

    private MainView mMainView;

    /**
     * Construct to link presenter and view.
     * Allow the presenter to pass data from model to view.
     * @param view
     */
    public MainActivityPresenter(MainView view) {
        mMainView = view;
    }

    /**
     * Confirm if there is a user logged in and calls the appropriate view method.
     * @param email
     */
    public void checkLoggedIn(String email) {
        if (email==null||email.equals("")) {
            mMainView.navigateToLogin();
        } else {
            List<User> userList = User.find(User.class, "M_EMAIL = ?", email);
            User user = userList.get(0);
            mMainView.logUserIn(user.getFirstName());
        }
    }

}
