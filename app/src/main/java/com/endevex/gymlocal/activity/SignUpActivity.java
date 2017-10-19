package com.endevex.gymlocal.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.endevex.gymlocal.R;
import com.endevex.gymlocal.presenter.SignUpActivityPresenter;
import com.endevex.gymlocal.utils.Constants;
import com.endevex.gymlocal.view.SignUpView;

public class SignUpActivity extends AppCompatActivity implements SignUpView {

    private SignUpActivityPresenter mPresenter;
    private EditText mFirstNameEt;
    private EditText mLastNameEt;
    private EditText mEmailEt;
    private EditText mPasswordEt;
    private EditText mPasswordRetypedEt;
    private RadioButton mGymUserRb;
    private RadioButton mGymOwnerRb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mPresenter = new SignUpActivityPresenter(this);
        mFirstNameEt = (EditText) findViewById(R.id.first_name_et);
        mLastNameEt = (EditText) findViewById(R.id.last_name_et);
        mEmailEt = (EditText) findViewById(R.id.email_et);
        mPasswordEt = (EditText) findViewById(R.id.password_et);
        mPasswordRetypedEt = (EditText) findViewById(R.id.password_retyped_et);
        mGymUserRb = (RadioButton) findViewById(R.id.gym_user_rb);
        mGymOwnerRb = (RadioButton) findViewById(R.id.gym_owner_rb);


    }

    public void registerUser(View view) {
        mPresenter.registerUser(mFirstNameEt.getText().toString(),
                mLastNameEt.getText().toString(),
                mEmailEt.getText().toString(),
                mPasswordEt.getText().toString(),
                mPasswordRetypedEt.getText().toString(), mGymOwnerRb.isChecked());
    }

    public void cancelRegister(View view) {
        finish();
    }

    @Override
    public void showErrorFirstName() {
        Toast.makeText(this, getString(R.string.enter_first_name), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorLastName() {
        Toast.makeText(this, getString(R.string.enter_last_name), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorEmail() {
        Toast.makeText(this, getString(R.string.enter_email), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorValidEmail() {
        Toast.makeText(this, getString(R.string.enter_valid_email), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorPassword() {
        Toast.makeText(this, getString(R.string.enter_password), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorPasswordMatch() {
        Toast.makeText(this, getString(R.string.password_error_match), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorEmailExists() {
        Toast.makeText(this, getString(R.string.email_exists), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void returnLoginUser(String email, String password) {
        Intent registeredUser = new Intent();
        registeredUser.putExtra(Constants.USER_EMAIL, email);
        registeredUser.putExtra(Constants.USER_PASSWORD, password);
        setResult(Activity.RESULT_OK, registeredUser);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
