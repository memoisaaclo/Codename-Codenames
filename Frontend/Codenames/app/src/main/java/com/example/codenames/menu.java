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
        exit = (Button) findViewById(R.id.userInfo_exit);

        //button click listener
        play.setOnClickListener(this);
        login.setOnClickListener(this);
        info.setOnClickListener(this);
        exit.setOnClickListener(this);

        if(username == null) {
            info.setVisibility(View.GONE);
            play.setText("Spectate");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.menu_login) {
            startActivity(new Intent(menu.this, login.class).putExtra("username", username));
        } else if (v.getId() == R.id.menu_play) {
            // play game
            if (username != null) {
                startActivity(new Intent(menu.this, HubActivity.class).putExtra("username", username));
            } else {
                /*
                Add Viewer Functionality, only access to join game lobby not at user.
                 */
                startActivity(new Intent(menu.this, spectatorHub.class));
            }
        } else if (v.getId() == R.id.menu_user) {
            // user info connection

            startActivity(new Intent(menu.this, userInfo.class).putExtra("username", username));
        } else if (v.getId() == R.id.userInfo_exit) {
            //exit app
            finish();
        }
    }
}