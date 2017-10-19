package com.endevex.gymlocal.presenter;

import android.util.Log;

import com.endevex.gymlocal.model.Gym;
import com.endevex.gymlocal.model.User;
import com.endevex.gymlocal.view.RegisterGymView;

import java.util.List;

/**
 * Accepts requests from RegisterGymActivity and handles data process in the persistence of the
 * application and models.
 */
public class RegisterGymActivityPresenter {
    private int mQueryLimitCounter = 0;
    private static final String TAG = "REGISTERGYMPRESENTER";

    private RegisterGymView mRegisterGymView;

    /**
     * Constructor for the class which initialises the view.
     *
     * @param view Received from RegisterActivity attempted to follow MVP Architecture.
     */
    public RegisterGymActivityPresenter(RegisterGymView view) {
        this.mRegisterGymView = view;
    }

    /**
     * Kicks off the save gym process by checking data is valid and then starting the
     * background service to get the geo co-ordinates of the address provided.
     *
     * @param gymName
     * @param gymType
     * @param subpremise
     * @param streetNumber
     * @param street
     * @param suburb
     * @param state
     * @param country
     * @param postcode
     * @param phone
     */
    public void saveGym(String gymName, String gymType, String subpremise, String streetNumber,
                        String street, String suburb, String state, String country,
                        String postcode, String phone) {

        if (validated(gymName, gymType, streetNumber, street, suburb, postcode, phone)) {

            String address = makeAddress(subpremise, streetNumber, street, suburb, state,
                    country, postcode);

            mRegisterGymView.startBackgroundService(address);
        }
    }

    /**
     * Confirms data is valid and validates.
     *
     * @param gymName
     * @param gymType
     * @param streetNumber
     * @param street
     * @param suburb
     * @param postcode
     * @param phone
     * @return true if data is good.
     */
    private boolean validated(String gymName, String gymType,
                              String streetNumber, String street, String suburb,
                              String postcode, String phone) {
        if (gymName.isEmpty()) {
            mRegisterGymView.errorGymNameEmpty();
            return false;
        }
        if (gymType.isEmpty()) {
            mRegisterGymView.errorGymTypeEmpty();
            return false;
        }
        if (phone.isEmpty()) {
            mRegisterGymView.errorGymPhoneEmpty();
            return false;
        }
        if (streetNumber.isEmpty()) {
            mRegisterGymView.errorStreetNumberEmpty();
            return false;
        }
        if (street.isEmpty()) {
            mRegisterGymView.errorStreetEmpty();
            return false;
        }
        if (suburb.isEmpty()) {
            mRegisterGymView.errorSuburbEmpty();
            return false;
        }
        if (postcode.isEmpty()) {
            mRegisterGymView.errorPostCodeEmpty();
            return false;
        }
        return true;
    }


    /**
     * Concatenates a string and returns depending on subpremise is used or not.
     *
     * @param subpremise
     * @param streetNumber
     * @param street
     * @param suburb
     * @param state
     * @param country
     * @param postcode
     * @return address concatenated.
     */
    private String makeAddress(String subpremise, String streetNumber, String street,
                               String suburb, String state, String country, String postcode) {
        if (subpremise.isEmpty()) {
            return streetNumber + "+" + street + ",+" + suburb + ",+" + state + ",+" + country
                    + ",+" + postcode;
        }
        return subpremise + "/" + streetNumber + "+" + street + ",+" + suburb + ",+" + state + ",+"
                + country + ",+" + postcode;
    }

    /**
     * Saves gym to local database using Sugar ORM
     *
     * @param gymName
     * @param gymType
     * @param subpremise
     * @param streetNumber
     * @param street
     * @param suburb
     * @param state
     * @param country
     * @param postcode
     * @param phone
     * @param latitude
     * @param longitude
     * @param emailLoggedInUser
     */
    public void saveGym(String gymName, String gymType, String subpremise, String streetNumber,
                        String street, String suburb, String state, String country,
                        String postcode, String phone, double latitude, double longitude,
                        String emailLoggedInUser) {

        String address = makeAddress(subpremise, streetNumber, street, suburb, state,
                country, postcode);

        List<User> userList = User.find(User.class, "M_EMAIL = ?", emailLoggedInUser);
        Gym gym = new Gym(gymName, gymType, address, phone, longitude, latitude, userList.get(0));
        gym.save();
        List<Gym> gm = Gym.listAll(Gym.class);
        for (Gym g : gm) {
            Log.d(TAG, g.toString());
        }
    }
}
