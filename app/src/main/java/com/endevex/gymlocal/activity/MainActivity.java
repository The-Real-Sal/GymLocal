package com.endevex.gymlocal.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.endevex.gymlocal.R;
import com.endevex.gymlocal.presenter.MainActivityPresenter;
import com.endevex.gymlocal.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView {

    private MainActivityPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        mPresenter = new MainActivityPresenter(this, sp);
        mPresenter.checkLoggedIn();
    }


    @Override
    public void navigateToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
