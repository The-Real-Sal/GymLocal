package com.endevex.gymlocal.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.endevex.gymlocal.R;
import com.endevex.gymlocal.presenter.AccountActivityPresenter;
import com.endevex.gymlocal.utils.Constants;
import com.endevex.gymlocal.view.AccountView;

public class AccountActivity extends AppCompatActivity implements AccountView {

    private SharedPreferences mSharedPref;
    private AccountActivityPresenter mPresenter;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private TextView mUserTypeTv;
    private String mCurrentEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mFirstName = (EditText) findViewById(R.id.first_name_et);
        mLastName = (EditText) findViewById(R.id.last_name_et);
        mEmail = (EditText) findViewById(R.id.email_et);
        mUserTypeTv = (TextView) findViewById(R.id.account_type_tv);
        mSharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        mPresenter = new AccountActivityPresenter(this);
        mPresenter.loadUserDetails(mSharedPref.getString(Constants.LOGGED_IN_USER_EMAIL,null));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void loadAccountDetails(String firstName, String lastName, String email, String userType) {
        mFirstName.setText(firstName);
        mLastName.setText(lastName);
        mEmail.setText(email);
        mUserTypeTv.setText(userType);
        mCurrentEmail = email;
    }

    public void cancelChanges(View view) {
        finish();
    }

    public void saveChanges(View view) {
        mPresenter.saveUserDetails(mFirstName.getText().toString(), mLastName.getText().toString(), mCurrentEmail, mEmail.getText().toString());
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putString(Constants.LOGGED_IN_USER_EMAIL, mEmail.getText().toString());
        editor.commit();
        finish();
    }

    public void changePassword(View view) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

}
