package com.endevex.gymlocal.view;

/**
 * View for Account. Project architecture is MVP
 * Created by Sal on 13/10/17.
 */

public interface AccountView {
    void loadAccountDetails(String firstName, String lastName, String email, String userType);
}
