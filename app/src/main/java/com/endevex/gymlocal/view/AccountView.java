package com.endevex.gymlocal.view;

/**
 * View for Account. Project architecture is MVP
 * Created by Sal on 13/10/17.
 */
public interface AccountView {
    /**
     * Save the changes made to the users account
     */
    void saveChanges();

    /**
     * Loads the users account details in to the edit text fields.
     * @param firstName
     * @param lastName
     * @param email
     * @param userType
     */
    void loadAccountDetails(String firstName, String lastName, String email, String userType);
}
