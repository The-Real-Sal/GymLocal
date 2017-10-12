package com.endevex.gymlocal.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.endevex.gymlocal.R;
import com.endevex.gymlocal.presenter.MainActivityPresenter;
import com.endevex.gymlocal.utils.Constants;
import com.endevex.gymlocal.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView {

    private MainActivityPresenter mPresenter;
    private EditText mEmailEt;
    private EditText mPasswordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainActivityPresenter(this);
        mEmailEt = (EditText) findViewById(R.id.email_et);
        mPasswordEt = (EditText) findViewById(R.id.password_et);
    }

    public void showRegisterActivity(View view) {
        startActivityForResult(new Intent(this, RegisterActivity.class), Constants.REQUEST_CODE);
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

    public void attemptLogin(View view) {
        mPresenter.login(mEmailEt.getText().toString(), mPasswordEt.getText().toString());
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(MainActivity.this, getString(R.string.enter_email_password_msg), Toast.LENGTH_SHORT).show();
    }

}
