package com.endevex.gymlocal.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.endevex.gymlocal.R;
import com.endevex.gymlocal.presenter.RegisterPresenter;
import com.endevex.gymlocal.view.RegisterView;

/**
 * Created by Leivant on 6/10/2017.
 */

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    private RegisterPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mPresenter = new RegisterPresenter(this);

    }

    public void cancelRegister(View view) {
        finish();
    }

    @Override
    public void showErrorFirstName() {
        Toast.makeText(RegisterActivity.this, getString(R.string.enter_first_name), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorLastName() {
        Toast.makeText(RegisterActivity.this, getString(R.string.enter_last_name), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorEmail() {
        Toast.makeText(RegisterActivity.this, getString(R.string.enter_email), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorValidEmail() {
        Toast.makeText(RegisterActivity.this, getString(R.string.enter_valid_email), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorPassword() {
        Toast.makeText(RegisterActivity.this, getString(R.string.enter_password), Toast.LENGTH_SHORT).show();
    }
}
