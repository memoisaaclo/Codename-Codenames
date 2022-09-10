package com.example.newmenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class random extends AppCompatActivity
{
    private Button rollbutton;
    private Button backbutton2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.random);

        rollbutton = findViewById(R.id.rollbutton);
        backbutton2 = findViewById(R.id.backbutton2);
        Random rand = new Random();
        final int[] coin_flip = {rand.nextInt(2)};

        rollbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                coin_flip[0] = (rand.nextInt(2));
                TextView roll_tv = findViewById(R.id.rolltext);
                //Intent intent = new Intent(getApplicationContext(), hello_world.class);

                if (coin_flip[0] == 1)
                {
                    roll_tv.setText("HEADS");
                }
                else
                {
                    roll_tv.setText("TAILS");
                }
            }
        });

        backbutton2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(random.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
