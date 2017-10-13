package com.endevex.gymlocal.presenter;

import com.endevex.gymlocal.activity.AccountActivity;
import com.endevex.gymlocal.model.User;
import com.endevex.gymlocal.view.AccountView;

import java.util.List;

/**
 * Activity's presenter. Where all the Logic gets done for the Account Activity.
 * Created by Sal on 13/10/17.
 */

public class AccountActivityPresenter {
    private AccountView mAccountView;
    public AccountActivityPresenter(AccountView view){
        mAccountView = view;
    }

    public void loadUserDetails(String email){
        List<User> userList = User.find(User.class, "M_EMAIL = ?", email);
        User user = userList.get(0);
        String userType = user.isGymOwner() ? "Gym Owner" : "Gym User";
        mAccountView.loadAccountDetails(user.getFirstName(), user.getLastName(), user.getEmail(), userType);
    }

    public void saveUserDetails(String firstName, String lastName, String currentEmail, String newEmail) {
        List<User> userList = User.find(User.class, "M_EMAIL = ?", currentEmail);
        User user = userList.get(0);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(newEmail);
        user.save();
    }

    public void saveUserDetails(String firstName, String lastName, String currentEmail, String newEmail, String password) {

    }
}
