package com.endevex.gymlocal.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.endevex.gymlocal.R;
import com.endevex.gymlocal.presenter.FindGymActivityPresenter;
import com.endevex.gymlocal.view.FindGymView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Displays a map which places markers around the area you are viewing.
 * Markers will display additional information about themselves and their gym.
 */
public class FindGymActivity extends FragmentActivity implements OnMapReadyCallback, FindGymView {

    private GoogleMap mMap;
    private FindGymActivityPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_gym);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mPresenter = new FindGymActivityPresenter(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        // Set a preference for minimum and maximum zoom.
        mMap.setMinZoomPreference(11.0f);
        mMap.setMaxZoomPreference(17.0f);
        mPresenter.loadMarkers();
        mMap.setInfoWindowAdapter(new GymInfoWindowAdapter());
    }

    @Override
    public void addMarker(Double latitude, Double longitude, String name) {
        LatLng latLngMarker = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(latLngMarker).title(name));

    }

    /**
     * A window adapter that changes the default marker info window to a more suitable and
     * customisable look to display information about the gyms on the map.
     */
    class GymInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{

        private final View mGymInfoView;

        /**
         * Constructor that assigns the view to the adapter.
         */
        GymInfoWindowAdapter() {
            mGymInfoView = getLayoutInflater().inflate(R.layout.content_map_info, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {

            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            // Grab the Text views from the myGymInfoView.
            TextView gymNameTv = (TextView)mGymInfoView.findViewById(R.id.gym_name_tv);
            TextView gymTypeTv = (TextView)mGymInfoView.findViewById(R.id.gym_type_tv);
            TextView gymPhoneTv = (TextView)mGymInfoView.findViewById(R.id.gym_phone_tv);

            // Grab the title of the marker which has a concatenation of gym name, type and phone.
            String wholeString = marker.getTitle();

            // Break apart the string into it's correct variables which have been delimited by '~'
            String gymName = wholeString.substring(0,wholeString.indexOf('~'));
            wholeString = wholeString.substring(gymName.length()+1, wholeString.length()-1);
            String gymType = wholeString.substring(0,wholeString.indexOf('~'));
            String gymPhone = wholeString.substring(gymType.length()+1, wholeString.length()-1);
            // Set text in the view.
            gymNameTv.setText(gymName);
            gymTypeTv.setText(gymType);
            gymPhoneTv.setText(gymPhone);

            return mGymInfoView;
        }
    }
}
