package com.example.codenames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class userInfo extends AppCompatActivity implements View.OnClickListener {

    private Button exit;
    private TextView gamesWon;
    private TextView gamesPlayed;
    private TextView guessesMade;
    private TextView cluesGiven;
    private TextView correctGuesses;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        exit = (Button) findViewById(R.id.userInfo_exit);

        exit.setOnClickListener(this);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.userInfo_exit) {
            startActivity(new Intent(userInfo.this, menu.class).putExtra("username", username));
        }
    }
}