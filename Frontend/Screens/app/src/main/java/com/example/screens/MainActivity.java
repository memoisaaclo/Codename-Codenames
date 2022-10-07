package com.example.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button play;
    private Button login;
    private Button info;
    private Button exit;
    private TextView account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //buttons
        play = (Button) findViewById(R.id.menu_play);
        login = (Button) findViewById(R.id.menu_login);
        info = (Button) findViewById(R.id.menu_user);
        exit = (Button) findViewById(R.id.menu_exit);

        //button click listener
        play.setOnClickListener(this);
        login.setOnClickListener(this);
        info.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.menu_login) {
            startActivity(new Intent(MainActivity.this, login.class));
        } else if (v.getId() == R.id.menu_play) {
            // play game
            //startActivity(new Intent(MainActivity.this, FILL IN FROM JIMMY));
        } else if (v.getId() == R.id.menu_user) {
            // user info connection
            //startActivity(new Intent(MainActivity.this, userInfo.class));
        } else if (v.getId() == R.id.menu_exit) {
            //exit app
            finish();
        }
    }
}