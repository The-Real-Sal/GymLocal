package com.endevex.gymlocal.view;

/**
 * Created by Leivant on 20/10/2017.
 */

public interface EditGymView {
    void saveGym();

    void loadGymDetails(String gymName, String gymType, String phone);

    void errorGymName();

    void errorGymType();

    void errorPhone();

    void displayToastMessage(String message);
}
