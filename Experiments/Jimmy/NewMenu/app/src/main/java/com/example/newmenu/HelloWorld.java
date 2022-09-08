package com.example.newmenu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class HelloWorld extends AppCompatActivity
{
    private Button button;
    private Button button2;
    private Button button3;
    private Button backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello_world);

        button = findViewById(R.id.txtbutton);
        button2 = findViewById(R.id.txtbutton2);
        button3 = findViewById(R.id.txtbutton3);
        backbutton = findViewById(R.id.backbutton);

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

        backbutton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(HelloWorld.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
