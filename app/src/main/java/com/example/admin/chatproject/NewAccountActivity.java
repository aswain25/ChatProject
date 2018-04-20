package com.example.admin.chatproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.admin.chatproject.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewAccountActivity extends AppCompatActivity {

    @BindView(R.id.etEmail) EditText etEmail;
    @BindView(R.id.etPassword) EditText etPassword;
    @BindView(R.id.etFirstName) EditText etFirstName;
    @BindView(R.id.etLastName) EditText etLastName;
    @BindView(R.id.etAge) EditText etAge;

    DBManager databaseManager;
    AuthenticationManager authenticationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
        AuthenticationManager.initialize(this);
        authenticationManager = AuthenticationManager.getDefault();
        databaseManager = new DBManager();
        ButterKnife.bind(this);
    }

    public void btnCreate_Clicked(View view)
    {
        authenticationManager.register(etEmail.getText().toString(), etPassword.getText().toString());
        User newUser = new User(etEmail.getText().toString(), etFirstName.getText().toString(), etLastName.getText().toString(), etAge.getText().toString());
        databaseManager.addUser(newUser);

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
