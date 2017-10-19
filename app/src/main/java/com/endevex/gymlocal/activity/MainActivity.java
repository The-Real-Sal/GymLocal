package com.endevex.gymlocal.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.endevex.gymlocal.R;
import com.endevex.gymlocal.presenter.MainActivityPresenter;
import com.endevex.gymlocal.utils.Constants;
import com.endevex.gymlocal.view.MainView;

/**
 * Main component of the app.
 * It checks if a user is logged in and allows them to view it, otherwise send them to login.
 * It displays certain layouts depending on the user type logged in.
 */
public class MainActivity extends AppCompatActivity implements MainView {

    private MainActivityPresenter mPresenter;
    private TextView mWelcomeTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mWelcomeTv = (TextView) findViewById(R.id.welcome_message_tv);
        mPresenter = new MainActivityPresenter(this);
        checkLoggedIn();
    }

    /**
     * Check if user is logged in.
     */
    private void checkLoggedIn() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        // Grab email of logged in user and check if logged in by passing to Presenter method.
        String email = sharedPref.getString(Constants.LOGGED_IN_USER_EMAIL, "");
        mPresenter.checkLoggedIn(email);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkLoggedIn();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_show_account:
                goToAccount();
                break;
            case R.id.action_logout:
                logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void navigateToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void logUserIn(String name) {
        mWelcomeTv.setText(getString(R.string.welcome_user, name));
    }

    /**
     * Logs the user out
     */
    public void logout() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constants.LOGGED_IN_USER_EMAIL, "");
        editor.commit();
        mPresenter.checkLoggedIn(sharedPref.getString(Constants.LOGGED_IN_USER_EMAIL, null));
    }

    /**
     * On click method. Sends user to account details.
     */
    public void goToAccount() {
        startActivity(new Intent(this, AccountActivity.class));
    }

    /**
     * On click method. Send user to Map view to find gym.
     * @param view
     */
    public void startMapActivity(View view) {
        startActivity(new Intent(this, FindGymActivity.class));
    }

    /**
     * On click method. Send user to add gym activity.
     * @param view
     */
    public void goToAddGym(View view) {
        startActivity(new Intent(this, RegisterGymActivity.class));
    }

    /**
     *  On click method. Send user to edit gym activity.
     *
     * @param view
     */
    public void goToEditGym(View view) {
        startActivity(new Intent(this, EditGymActivity.class));
    }
}
