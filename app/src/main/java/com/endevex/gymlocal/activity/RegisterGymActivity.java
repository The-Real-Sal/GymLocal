package com.endevex.gymlocal.activity;

import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.endevex.gymlocal.R;
import com.endevex.gymlocal.model.geocode.GeocodeResult;
import com.endevex.gymlocal.model.geocode.Location;
import com.endevex.gymlocal.presenter.RegisterGymPresenter;
import com.endevex.gymlocal.services.BackgroundServiceGeocode;
import com.endevex.gymlocal.services.GeocodeService;
import com.endevex.gymlocal.utils.Constants;
import com.endevex.gymlocal.view.RegisterGymView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.defaultValue;

public class RegisterGymActivity extends AppCompatActivity implements RegisterGymView {

    private RegisterGymPresenter mPresenter;
    private EditText mGymName;
    private EditText mGymType;
    private EditText mGymPhone;
    private EditText mUnitNumber;
    private EditText mStreetNumber;
    private EditText mStreet;
    private EditText mSuburb;
    private EditText mPostCode;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("receiver", "Got message: ");
            double gymLatitude = Double.parseDouble(intent.getStringExtra(Constants.LOCATION_LATITUDE));
            double gymLongitude = Double.parseDouble(intent.getStringExtra(Constants.LOCATION_LONGITUDE));
            saveGym(gymLatitude, gymLongitude);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_gym);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mPresenter = new RegisterGymPresenter(this);
        mGymName = (EditText) findViewById(R.id.gym_name_et);
        mGymType = (EditText) findViewById(R.id.gym_type_et);
        mGymPhone = (EditText) findViewById(R.id.phone_et);
        mUnitNumber = (EditText) findViewById(R.id.unit_number_et);
        mStreetNumber = (EditText) findViewById(R.id.street_number_et);
        mStreet = (EditText) findViewById(R.id.street_et);
        mSuburb = (EditText) findViewById(R.id.suburb_et);
        mPostCode = (EditText) findViewById(R.id.postcode_et);
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver, new IntentFilter(Constants.GEOCODE_RESULTS_UPDATE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_account, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveGym();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void saveGym() {
        mPresenter.saveGym(mGymName.getText().toString(), mGymType.getText().toString(),
                mUnitNumber.getText().toString(), mStreetNumber.getText().toString(),
                mStreet.getText().toString(), mSuburb.getText().toString(),
                Constants.STATE, Constants.COUNTRY, mPostCode.getText().toString(),
                mGymPhone.getText().toString());
    }

    @Override
    public void saveGym(double latitude, double longitude) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        mPresenter.saveGym(mGymName.getText().toString(), mGymType.getText().toString(),
                mUnitNumber.getText().toString(), mStreetNumber.getText().toString(),
                mStreet.getText().toString(), mSuburb.getText().toString(),
                Constants.STATE, Constants.COUNTRY, mPostCode.getText().toString(),
                mGymPhone.getText().toString(), latitude, longitude,
                sharedPref.getString(Constants.LOGGED_IN_USER_EMAIL, null));
    }

    @Override
    public void errorGymLocation() {
        displayToastMessage(getString(R.string.error_gym_address));
    }

    @Override
    public void errorGymNameEmpty() {
        mGymName.setError(getString(R.string.error_enter_gym_name));
        displayToastMessage(getString(R.string.error_enter_gym_name));
    }

    @Override
    public void errorGymTypeEmpty() {
        mGymType.setError(getString(R.string.hint_gym_type));
        displayToastMessage(getString(R.string.error_enter_gym_type));
    }

    @Override
    public void errorGymPhoneEmpty() {
        mGymPhone.setError(getString(R.string.error_enter_phone));
        displayToastMessage(getString(R.string.error_enter_phone));
    }

    @Override
    public void errorStreetNumberEmpty() {
        mStreetNumber.setError(getString(R.string.error_enter_street_number));
        displayToastMessage(getString(R.string.error_enter_street_number));
    }

    @Override
    public void errorStreetEmpty() {
        mStreet.setError(getString(R.string.error_enter_street));
        displayToastMessage(getString(R.string.error_enter_street));
    }

    @Override
    public void errorSuburbEmpty() {
        mSuburb.setError(getString(R.string.error_enter_suburb));
        displayToastMessage(getString(R.string.error_enter_suburb));
    }

    @Override
    public void errorPostCodeEmpty() {
        mPostCode.setError(getString(R.string.error_enter_postcode));
        displayToastMessage(getString(R.string.error_enter_postcode));
    }

    @Override
    public void displayToastMessage(String message) {
        Toast.makeText(RegisterGymActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    public void startBackgroundService(String address) {
        Intent backgroundIntent = new Intent(RegisterGymActivity.this, BackgroundServiceGeocode.class);
        backgroundIntent.putExtra(Constants.ADDRESS, address);
        backgroundIntent.putExtra(Constants.API_KEY, getString(R.string.google_maps_key));
        startService(backgroundIntent);
    }


}
