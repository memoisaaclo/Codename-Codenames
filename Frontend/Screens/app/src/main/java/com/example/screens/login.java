package com.example.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity implements View.OnClickListener {

    private Button login;
    private Button register;
    private Button exit;
    private TextView username;
    private TextView pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //buttons
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.login_register);
        exit = (Button) findViewById(R.id.login_exit);

        //edit texts
        username = (TextView) findViewById(R.id.login_user);
        pass = (TextView) findViewById(R.id.login_password);

        //button click listeners
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        exit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.login) {
            startActivity(new Intent(login.this, menu.class));
        } else if (v.getId() == R.id.register) {
            startActivity((new Intent(login.this, registration.class)));
        } else if (v.getId() == R.id.login_exit) {
            startActivity(new Intent(login.this, menu.class));
        }

    }
}