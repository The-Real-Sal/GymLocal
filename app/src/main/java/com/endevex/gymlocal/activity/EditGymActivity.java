package com.endevex.gymlocal.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.endevex.gymlocal.R;
import com.endevex.gymlocal.presenter.EditGymActivityPresenter;
import com.endevex.gymlocal.utils.Constants;
import com.endevex.gymlocal.view.EditGymView;

public class EditGymActivity extends AppCompatActivity implements EditGymView {

    private EditGymActivityPresenter mPresenter;
    private EditText mGymName;
    private EditText mGymType;
    private EditText mGymPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_gym);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mPresenter = new EditGymActivityPresenter(this);
        mGymName = (EditText) findViewById(R.id.gym_name_et);
        mGymType = (EditText) findViewById(R.id.gym_type_et);
        mGymPhone = (EditText) findViewById(R.id.phone_et);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        mPresenter.loadGymDetails(sharedPref.getString(Constants.LOGGED_IN_USER_EMAIL, null));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_account, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void saveGym() {
        mPresenter.saveGym(mGymName.getText().toString(), mGymType.getText().toString(),
                mGymPhone.getText().toString());
        finish();
    }

    @Override
    public void loadGymDetails(String gymName, String gymType, String phone) {
        mGymName.setText(gymName);
        mGymType.setText(gymType);
        mGymPhone.setText(phone);
    }

    @Override
    public void errorGymName() {
        mGymName.setError(getString(R.string.error_enter_gym_name));
        displayToastMessage(getString(R.string.error_enter_gym_name));
    }

    @Override
    public void errorGymType() {
        mGymType.setError(getString(R.string.error_enter_gym_type));
        displayToastMessage(getString(R.string.error_enter_gym_type));
    }

    @Override
    public void errorPhone() {
        mGymPhone.setError(getString(R.string.error_enter_phone));
        displayToastMessage(getString(R.string.error_enter_phone));
    }

    @Override
    public void displayToastMessage(String message) {
        Toast.makeText(EditGymActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
