package com.example.exp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private SeekBar numSides;
    private Random rand;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.roll);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                result = generateNum();
                ((TextView)findViewById(R.id.rand_num)).setText(result);

            }
        });
    }

    private String generateNum() {
        numSides = (SeekBar) findViewById(R.id.seekBar);
        rand = new Random();
        return Integer.toString(rand.nextInt(numSides.getProgress()) + 1);
    }
}