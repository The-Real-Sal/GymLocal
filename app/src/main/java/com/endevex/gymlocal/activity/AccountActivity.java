package com.endevex.gymlocal.activity;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.endevex.gymlocal.R;
import com.endevex.gymlocal.presenter.AccountActivityPresenter;
import com.endevex.gymlocal.presenter.LoginActivityPresenter;
import com.endevex.gymlocal.utils.Constants;
import com.endevex.gymlocal.view.AccountView;

/**
 * Activity where user can update their account details.
 * Created by Sal on 13/10/17.
 */

public class AccountActivity extends AppCompatActivity implements AccountView {

    private SharedPreferences mSharedPref;
    private AccountActivityPresenter mPresenter;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private String mCurrentEmail;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mFirstName = (EditText) findViewById(R.id.first_name_et);
        mLastName = (EditText) findViewById(R.id.last_name_et);
        mEmail = (EditText) findViewById(R.id.email_et);
        mSharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        mPresenter = new AccountActivityPresenter(this);
        mPresenter.loadUserDetails(mSharedPref.getString(Constants.LOGGED_IN_USER_EMAIL,null));
    }

    @Override
    public void loadAccountDetails(String firstName, String lastName, String email) {
        mFirstName.setText(firstName);
        mLastName.setText(lastName);
        mEmail.setText(email);
        mCurrentEmail = email;
    }

    public void cancelChanges(View view) {
        finish();
    }

    public void saveChanges(View view) {
        mPresenter.saveUserDetails(mFirstName.getText().toString(), mLastName.getText().toString(), mCurrentEmail, mEmail.getText().toString());
        Editor editor = mSharedPref.edit();
        editor.putString(Constants.LOGGED_IN_USER_EMAIL, mEmail.getText().toString());
        editor.commit();
        finish();
    }

    public void changePassword(View view) {

    }
}
