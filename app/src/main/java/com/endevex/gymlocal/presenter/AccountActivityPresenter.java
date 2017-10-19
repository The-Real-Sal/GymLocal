package com.endevex.gymlocal.presenter;

import com.endevex.gymlocal.model.User;
import com.endevex.gymlocal.view.AccountView;

import java.util.List;

/**
 * Activity's presenter. Where all the Logic gets done for the Account Activity.
 * Essentially the loading and editing of the users profile.
 * Created by Sal on 13/10/17.
 */
public class AccountActivityPresenter {

    private AccountView mAccountView;

    /**
     * Constructor takes a view of the activity so it can call methods or pass data.
     *
     * @param view
     */
    public AccountActivityPresenter(AccountView view) {
        mAccountView = view;
    }

    /**
     * Finds the logged in user and loads the details back into the view.
     *
     * @param email
     */
    public void loadUserDetails(String email) {
        List<User> userList = User.find(User.class, "M_EMAIL = ?", email);
        User user = userList.get(0);
        String userType = user.isGymOwner() ? "Gym Owner" : "Gym User";
        mAccountView.loadAccountDetails(user.getFirstName(), user.getLastName(), user.getEmail(), userType);
    }

    /**
     * Updates the user model and saves with Sugar ORM.
     *
     * @param firstName
     * @param lastName
     * @param currentEmail
     * @param newEmail
     */
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
