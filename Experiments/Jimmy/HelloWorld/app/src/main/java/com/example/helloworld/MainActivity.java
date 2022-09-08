package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private Button button2;
    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.txtbutton);
        button2 = findViewById(R.id.txtbutton2);
        button3 = findViewById(R.id.txtbutton3);

        Random rand = new Random();

        final int[] rand_int = {(rand.nextInt(32)) + 16};

        button.setOnClickListener(new View.OnClickListener() 
        {
            @Override
            public void onClick(View view) 
            {
                TextView tv = findViewById(R.id.txtview);
                //Change visibility
                tv.setVisibility(View.VISIBLE);
                //set a value in the textview
                tv.setText("Hello World");
            }
        });

        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                TextView tv = findViewById(R.id.txtview);
                //Change color
                tv.setTextColor(Color.parseColor("#FF0000"));
            }
        });

        button3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                rand_int[0] = (rand.nextInt(32)) + 16;
                TextView tv = findViewById(R.id.txtview);
                //Change size
                tv.setTextSize(rand_int[0]);
            }
        });
    }
}