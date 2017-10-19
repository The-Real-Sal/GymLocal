package com.endevex.gymlocal.view;

/**
 * View for the findGymActivity.
 * Created by Leivant on 20/10/2017.
 */
public interface FindGymView {

    /**
     * Creates a marker on the map at the position provided with the name provided.
     * @param latitude
     * @param longitude
     * @param name
     */
    void addMarker(Double latitude, Double longitude, String name);
}
