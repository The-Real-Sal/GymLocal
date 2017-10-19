package com.endevex.gymlocal.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.endevex.gymlocal.R;
import com.endevex.gymlocal.presenter.RegisterGymActivityPresenter;
import com.endevex.gymlocal.services.BackgroundServiceGeocode;
import com.endevex.gymlocal.utils.Constants;
import com.endevex.gymlocal.view.RegisterGymView;

public class RegisterGymActivity extends AppCompatActivity implements RegisterGymView {

    private RegisterGymActivityPresenter mPresenter;
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
        mPresenter = new RegisterGymActivityPresenter(this);
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

    /**
     * Initial save method that kicks off the save process, it calls the presenter to
     * validate the data and kick off background services if valid.
     */
    @Override
    public void saveGym() {
        mPresenter.saveGym(mGymName.getText().toString(), mGymType.getText().toString(),
                mUnitNumber.getText().toString(), mStreetNumber.getText().toString(),
                mStreet.getText().toString(), mSuburb.getText().toString(),
                Constants.STATE, Constants.COUNTRY, mPostCode.getText().toString(),
                mGymPhone.getText().toString());
    }

    /**
     * Once the background service has returned it call this method which then calls the
     * presenter to actually save the data with a longitude and latitude of the gym address
     * provided.
     * @param latitude
     * @param longitude
     */
    @Override
    public void saveGym(double latitude, double longitude) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        mPresenter.saveGym(mGymName.getText().toString(), mGymType.getText().toString(),
                mUnitNumber.getText().toString(), mStreetNumber.getText().toString(),
                mStreet.getText().toString(), mSuburb.getText().toString(),
                Constants.STATE, Constants.COUNTRY, mPostCode.getText().toString(),
                mGymPhone.getText().toString(), latitude, longitude,
                sharedPref.getString(Constants.LOGGED_IN_USER_EMAIL, null));
        finish();
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
