package com.endevex.gymlocal.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

public class MainActivity extends AppCompatActivity implements MainView {

    private MainActivityPresenter mPresenter;
    private SharedPreferences mSharedPref;
    private TextView mWelcomeTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mWelcomeTv = (TextView) findViewById(R.id.welcome_message_tv);
        mPresenter = new MainActivityPresenter(this);
        mSharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        // Grab email of logged in user and check if logged in by passing to Presenter method.
        String email = mSharedPref.getString(Constants.LOGGED_IN_USER_EMAIL, "");
        mPresenter.checkLoggedIn(email);

    }


    @Override
    protected void onRestart() {
        super.onRestart();

        // Grab email of logged in user and check if logged in by passing to Presenter method.
        String email = mSharedPref.getString(Constants.LOGGED_IN_USER_EMAIL, "");
        mPresenter.checkLoggedIn(email);
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


    public void logout() {
        mSharedPref = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putString(Constants.LOGGED_IN_USER_EMAIL, "");
        editor.commit();
        mPresenter.checkLoggedIn(mSharedPref.getString(Constants.LOGGED_IN_USER_EMAIL, null));
    }


    public void goToAccount() {
        startActivity(new Intent(this, AccountActivity.class));
    }

    public void startMapActivity(View view) {
        startActivity(new Intent(this, FindGymActivity.class));
    }

    public void goToAddGym(View view) {
        startActivity(new Intent(this, RegisterGymActivity.class));
    }
}
