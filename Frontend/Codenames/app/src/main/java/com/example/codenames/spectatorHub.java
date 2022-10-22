package com.example.codenames;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class spectatorHub extends AppCompatActivity implements View.OnClickListener {

    Button lobby1;
    Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spectator_hub);

        lobby1 = (Button) findViewById(R.id.button_lobby1);
        exit = (Button) findViewById(R.id.specHub_exit6);


    }

    @Override
    public void onClick(View v) {

    }
}