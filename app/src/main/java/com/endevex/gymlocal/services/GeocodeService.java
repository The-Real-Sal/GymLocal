package com.endevex.gymlocal.services;

import com.endevex.gymlocal.model.geocode.GeocodeResult;
import com.endevex.gymlocal.model.geocode.Location;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.QueryName;

/**
 * Created by Leivant on 18/10/2017.
 */

public interface GeocodeService {
    String BASE_URL = "https://maps.googleapis.com/";

    @Headers("Content-Type: application/json")
    @GET("maps/api/geocode/json")
    Call<GeocodeResult> getLocation(@Query(value = "address", encoded = true) String address,
                                    @QueryName String key);
}
