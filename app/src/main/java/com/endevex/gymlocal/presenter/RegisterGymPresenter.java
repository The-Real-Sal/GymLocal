package com.endevex.gymlocal.presenter;

import android.renderscript.Double2;
import android.util.Log;

import com.endevex.gymlocal.model.Gym;
import com.endevex.gymlocal.model.User;
import com.endevex.gymlocal.model.geocode.GeocodeResult;
import com.endevex.gymlocal.model.geocode.Location;
import com.endevex.gymlocal.model.geocode.Viewport;
import com.endevex.gymlocal.services.GeocodeService;
import com.endevex.gymlocal.utils.Constants;
import com.endevex.gymlocal.view.RegisterGymView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Accepts requests from RegisterGymActivity and handles data process in the persistence of the
 * application and models.
 */
public class RegisterGymPresenter {
    private int mQueryLimitCounter = 0;
    private static final String TAG = "REGISTERGYMPRESENTER";

    private RegisterGymView mRegisterGymView;

    /**
     * Constructor for the class which initialises the view.
     *
     * @param view Received from RegisterActivity attempted to follow MVP Architecture.
     */
    public RegisterGymPresenter(RegisterGymView view) {
        this.mRegisterGymView = view;
    }

    /**
     * Saves the gym and information passed through.
     * Calls methods to confirm data is valid.
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


    /**
     * Using the Retrofit API and Googles Geocode Webservice API this returns the longitude
     * and latitude of the address provided to check location can be found.
     *
     * @param address
     * @param apiKey
     * @return longitude and latitude in doubles.

    private void checkValidAddress(final String address, final String apiKey) {
    Retrofit retrofit = new Retrofit.Builder()
    .baseUrl(Constants.GEOCODE_WEBSERVICE_BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build();

    GeocodeService geoService = retrofit.create(GeocodeService.class);
    Call<GeocodeResult> call = geoService.getLocation(address, apiKey);

    call.enqueue(new Callback<GeocodeResult>() {
    @Override public void onResponse(Call<GeocodeResult> call, Response<GeocodeResult> response) {
    Log.d(TAG, "Response: " + response.toString());
    Log.d(TAG, "Received: " + response.body().toString());
    if (response.body().getStatus().equalsIgnoreCase(Constants.GEOCODE_QUERY_LIMIT)) {
    Log.d(TAG, "STUPID GOOGLE QUERY LIMIT!!!!!!");
    try {
    Thread.sleep(10000);
    mQueryLimitCounter++;
    if (mQueryLimitCounter < 5) {
    checkValidAddress(address, apiKey);
    } else {
    return;
    }
    } catch (InterruptedException e) {
    e.printStackTrace();
    }
    } else {
    Location locas  = response.body().getResults().get(0).getGeometry().getLocation();
    Log.d(TAG, "Lat: " + locas.getLat());
    Log.d(TAG, "Lng: " + locas.getLng());
    Viewport viepw = response.body().getResults().get(0).getGeometry().getViewport();
    Log.d(TAG, "13 Lat: " + viepw.getNortheast().getLng());
    Log.d(TAG, "13 Lng: " + viepw.getNortheast().getLat());
    Log.d(TAG, "13 Lat SW: " + viepw.getSouthwest().getLng());
    Log.d(TAG, "13 Lng SW: " + viepw.getSouthwest().getLat());
    }
    }

    @Override public void onFailure(Call<GeocodeResult> call, Throwable t) {
    mRegisterGymView.errorGymLocation();
    Log.e(TAG, "BAD: " + t.getMessage());
    Log.e(TAG, "BAD: " + t.getMessage());
    Log.e(TAG, "BAD: " + t.getMessage());
    }
    });
    }*/
}
