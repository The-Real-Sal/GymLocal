package com.endevex.gymlocal.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.endevex.gymlocal.model.geocode.GeocodeResult;
import com.endevex.gymlocal.model.geocode.Location;
import com.endevex.gymlocal.utils.Constants;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Leivant on 20/10/2017.
 */

public class BackgroundServiceGeocode extends IntentService {
    private final static String TAG = "BGSERVICEGEOCODE";
    private int mQueryLimitCounter;

    public BackgroundServiceGeocode() {
        super("BackgroundServiceGeocode");
    }

    /*@Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        mAddress = intent.getStringExtra(Constants.ADDRESS);
        mApiKey = intent.getStringExtra(Constants.API_KEY);
        return super.onStartCommand(intent, flags, startId);
    }*/

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String address = intent.getStringExtra(Constants.ADDRESS);
        String apiKey = intent.getStringExtra(Constants.API_KEY);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.GEOCODE_WEBSERVICE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GeocodeService geoService = retrofit.create(GeocodeService.class);
        Call<GeocodeResult> call = geoService.getLocation(address, apiKey);

        try {
            Response<GeocodeResult> result = call.execute();
            if (result.body().getStatus().equalsIgnoreCase(Constants.GEOCODE_QUERY_LIMIT)) {
                Log.d(TAG, "STUPID GOOGLE QUERY LIMIT!!!!!!");
                try {
                    Thread.sleep(10000);
                    mQueryLimitCounter++;
                    if (mQueryLimitCounter < 5) {
                        onHandleIntent(intent);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
                Location location = result.body().getResults().get(0).getGeometry().getLocation();
                Log.d(TAG, result.body().getResults().get(0).getGeometry().toString());
                Intent serviceIntent = new Intent(Constants.GEOCODE_RESULTS_UPDATE);
                serviceIntent.putExtra(Constants.LOCATION_LONGITUDE, String.valueOf(location.getLng()));
                serviceIntent.putExtra(Constants.LOCATION_LATITUDE,String.valueOf(location.getLat()));
                LocalBroadcastManager.getInstance(this.getApplicationContext()).sendBroadcast(serviceIntent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
