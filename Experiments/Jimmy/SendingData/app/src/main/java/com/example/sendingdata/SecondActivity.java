package com.example.sendingdata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity
{
    TextView receiver_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button backbutton = (Button) findViewById(R.id.backbutton);

        receiver_msg = findViewById(R.id.received_value_id);
        Intent intent = getIntent(); //creates get intent object
        String str = intent.getStringExtra("message_key"); //recieve value in getStringExtra
        receiver_msg.setText(str); //display string

        backbutton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}