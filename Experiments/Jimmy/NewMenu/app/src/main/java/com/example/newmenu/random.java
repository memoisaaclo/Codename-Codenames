package com.example.newmenu;

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

        final int[] rand_int = {(rand.nextInt(1))};

        rollbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                rand_int[0] = (rand.nextInt(2));
                TextView tv = findViewById(R.id.txtview);
                //Change visibility
                //tv.setVisibility(View.VISIBLE);
                //set a value in the textview
                if (rand_int[0] == 1) {
                    tv.setText("HEADS");
                }
                else
                {
                    tv.setText("TAILS");
                }
            }
        });
    }
}
