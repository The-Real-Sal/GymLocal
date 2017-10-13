package com.endevex.gymlocal.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.endevex.gymlocal.R;
import com.endevex.gymlocal.presenter.MainActivityPresenter;
import com.endevex.gymlocal.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView {

    private MainActivityPresenter mPresenter;
    private TextView mWelcomeTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWelcomeTv = (TextView) findViewById(R.id.welcome_message_tv);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        mPresenter = new MainActivityPresenter(this, sp);
        mPresenter.checkLoggedIn();
    }


    @Override
    public void navigateToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void logUserIn(String name) {
        mWelcomeTv.setText(getString(R.string.welcome_user, name));
    }


    public void logout(View view) {
        mPresenter.logOutUser();
    }
}
