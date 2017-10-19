package com.endevex.gymlocal.view;

/**
 * view for register gym activity where gym owners can register their gym.
 * Created by Leivant on 17/10/2017.
 */

public interface RegisterGymView {
    /**
     * Initial save method that kicks off the save process, it calls the presenter to
     * validate the data and kick off background services if valid.
     */
    void saveGym();

    /**
     * Once the background service has returned it call this method which then calls the
     * presenter to actually save the data with a longitude and latitude of the gym address
     * provided.
     * @param latitude
     * @param longitude
     */
    void saveGym(double latitude, double longitude);

    /**
     * Display error message for invalid gym name
     */
    void errorGymNameEmpty();

    /**
     * Display error message for invalid gym type
     */
    void errorGymTypeEmpty();

    /**
     * Display error message for invalid phone
     */
    void errorGymPhoneEmpty();

    /**
     * Display error message for invalid Street number
     */
    void errorStreetNumberEmpty();

    /**
     * Display error message for invalid street
     */
    void errorStreetEmpty();

    /**
     * Display error message for invalid suburb
     */
    void errorSuburbEmpty();

    /**
     * Display error message for invalid post code
     */
    void errorPostCodeEmpty();

    /**
     * Display error message on invalid gym location
     */
    void errorGymLocation();

    /**
     * Display a toast based on message param.
     * @param message
     */
    void displayToastMessage(String message);

    /**
     * Call the background service that will search the address using Retrofit and Google Geocode
     * API.
     * @param address
     */
    void startBackgroundService(String address);
}
