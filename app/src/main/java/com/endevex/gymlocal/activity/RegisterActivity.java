package com.endevex.gymlocal.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
        mPresenter = new RegisterPresenter();

    }

    public void cancelRegister(View view) {
        finish();
    }

    @Override
    public void showErrorFirstName() {

    }

    @Override
    public void showErrorLastName() {

    }

    @Override
    public void showErrorEmail() {

    }

    @Override
    public void showErrorPassword() {

    }
}
