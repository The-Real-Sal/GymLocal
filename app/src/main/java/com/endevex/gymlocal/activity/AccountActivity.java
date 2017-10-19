package com.endevex.gymlocal.activity;

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
import android.widget.EditText;
import android.widget.TextView;

import com.endevex.gymlocal.R;
import com.endevex.gymlocal.presenter.AccountActivityPresenter;
import com.endevex.gymlocal.utils.Constants;
import com.endevex.gymlocal.view.AccountView;

/**
 *  This is the activity of the users account (essentially a profile manager)
 *  User can come in edit their details.
 */
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

    }

    @Override
    public void loadAccountDetails(String firstName, String lastName, String email, String userType) {
        mFirstName.setText(firstName);
        mLastName.setText(lastName);
        mEmail.setText(email);
        mUserTypeTv.setText(userType);
        mCurrentEmail = email;
    }

    @Override
    public void saveChanges() {
        mPresenter.saveUserDetails(mFirstName.getText().toString(), mLastName.getText().toString(), mCurrentEmail, mEmail.getText().toString());
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putString(Constants.LOGGED_IN_USER_EMAIL, mEmail.getText().toString());
        editor.commit();
        finish();
    }

    /**
     * Onclick command to startActivity for change password
     * @param view
     */
    public void changePassword(View view) {

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
                saveChanges();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
