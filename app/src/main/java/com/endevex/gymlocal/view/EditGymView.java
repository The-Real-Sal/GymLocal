package com.endevex.gymlocal.view;

/**
 * The view model that edit gym activity will follow.
 * It is the view that the users of user type (Gym Owner) will see when updating their gym info.
 * Created by Leivant on 20/10/2017.
 */
public interface EditGymView {

    /**
     * Saves the details of the gym.
     */
    void saveGym();

    /**
     * Update the view with the gym details passed in by the following params.
     * @param gymName
     * @param gymType
     * @param phone
     */
    void loadGymDetails(String gymName, String gymType, String phone);

    /**
     * Display an error message of gym name not being valid.
     */
    void errorGymName();

    /**
     * Display an error message of gym type not being valid.
     */
    void errorGymType();

    /**
     * Display an error message of gym phone not being valid.
     */
    void errorPhone();

    /**
     * Display a toast with the message passed in from the param.
     * @param message
     */
    void displayToastMessage(String message);
}
