package com.endevex.gymlocal.view;

/**
 * Created by Leivant on 17/10/2017.
 */

public interface RegisterGymView {
    void saveGym();
    void saveGym(double latitude, double longitude);
    void errorGymNameEmpty();
    void errorGymTypeEmpty();
    void errorGymPhoneEmpty();
    void errorStreetNumberEmpty();
    void errorStreetEmpty();
    void errorSuburbEmpty();
    void errorPostCodeEmpty();
    void errorGymLocation();
    void displayToastMessage(String message);
    void startBackgroundService(String address);
}
