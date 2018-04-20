package com.example.admin.chatproject;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AuthenticationManager.IAuthenticationHandler {

    AuthenticationManager authenticationManager;

    @BindView(R.id.etEmail)
    EditText etUserName;

    @BindView(R.id.etPassword)
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AuthenticationManager.initialize(this);
        authenticationManager = AuthenticationManager.getDefault();

        ButterKnife.bind(this);
    }

    public void btnSignIn_Clicked(View view)
    {
        authenticationManager.sgnIn(etUserName.getText().toString(), etPassword.getText().toString());
    }

    public void btnRegister_Clicked(View view)
    {
        Intent intent = new Intent(getApplicationContext(), NewAccountActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoginSuccess(FirebaseUser user)
    {
        Intent intent = new Intent(getApplicationContext(), UsersViewActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSignOut(boolean isSignedOut)
    {

    }

    @Override
    public void onLoginError(String error)
    {

    }
}
