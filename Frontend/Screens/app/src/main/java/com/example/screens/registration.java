package com.example.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class registration extends Activity implements View.OnClickListener {

    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //button
        register = (Button) findViewById(R.id.register);

        //button click listener
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(registration.this, menu.class));
    }
}