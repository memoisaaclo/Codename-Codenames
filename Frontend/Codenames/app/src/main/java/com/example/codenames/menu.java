package com.example.codenames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.w3c.dom.Text;


public class menu extends AppCompatActivity implements View.OnClickListener{

    private Button play;
    private Button login;
    private Button info;
    private Button exit;
    private TextView account;
    private TextView menuUser;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();

        menuUser = (TextView) findViewById(R.id.menu_username);
        username = intent.getStringExtra("username");
        menuUser.setText(username);

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



        if(username == null) {
            info.setVisibility(View.GONE);
            play.setText("Spectate");
        } else {
            login.setText("Logout");
        }
        System.out.println("USERNAME: " + username);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.menu_login) {
            if (username == null) {
                //
                startActivity(new Intent(menu.this, login.class));
            } else {
                startActivity(new Intent(menu.this, menu.class));
            }
        } else if (v.getId() == R.id.menu_play) {
            // play game
            if (username != null) {
                //go to game hub
                startActivity(new Intent(menu.this, HubActivity.class).putExtra("username", username));
            } else if (username == null) {
                //go to spectator hub
                startActivity(new Intent(menu.this, spectatorHub.class));
            }
        } else if (v.getId() == R.id.menu_user) {
            // user info connection

            startActivity(new Intent(menu.this, userInfo.class).putExtra("username", username));
        } else if (v.getId() == R.id.menu_exit) {
            //exit app
            System.exit(0);
        }
    }
}