package com.endevex.gymlocal.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.endevex.gymlocal.R;
import com.endevex.gymlocal.presenter.LoginActivityPresenter;
import com.endevex.gymlocal.utils.Constants;
import com.endevex.gymlocal.view.LoginView;

/**
 * Activity where users can login with email and password.
 * Created by Sal on 13/10/2017.
 */
public class LoginActivity extends AppCompatActivity implements LoginView {

    private LoginActivityPresenter mPresenter;
    private SharedPreferences mSharedPref;
    private EditText mEmailEt;
    private EditText mPasswordEt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mSharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        mPresenter = new LoginActivityPresenter(this);
        mEmailEt = (EditText) findViewById(R.id.email_et);
        mPasswordEt = (EditText) findViewById(R.id.password_et);
    }

    /**
     * On click method to start Register Activity
     * @param view
     */
    public void showRegisterActivity(View view) {
        startActivityForResult(new Intent(this, SignUpActivity.class), Constants.REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    mEmailEt.setText(data.getStringExtra(Constants.USER_EMAIL));
                    mPasswordEt.setText(data.getStringExtra(Constants.USER_PASSWORD));
                    mPresenter.login(mEmailEt.getText().toString(), mPasswordEt.getText().toString());
                }
                break;
        }
    }

    /**
     * Attempts login by passing email, password to presenter.
     * @param view
     */
    public void attemptLogin(View view) {
        mPresenter.login(mEmailEt.getText().toString(), mPasswordEt.getText().toString());
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(LoginActivity.this, getString(R.string.enter_email_password_msg), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoginSuccess(String email) {
        mSharedPref = PreferenceManager.getDefaultSharedPreferences(this); //TODO : SHARED PREFERENCES and variable names
        Editor editor = mSharedPref.edit();
        editor.putString(Constants.LOGGED_IN_USER_EMAIL, email);
        editor.commit();
        finish();
    }

    @Override
    public void showLoginFailure() {
        Toast.makeText(LoginActivity.this, getString(R.string.login_failure), Toast.LENGTH_SHORT).show();
    }

}
