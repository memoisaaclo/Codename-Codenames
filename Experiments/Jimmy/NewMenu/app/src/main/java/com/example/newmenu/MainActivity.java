package com.example.newmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button toHelloWorld = (Button) findViewById(R.id.hellobutton);
        Button exitbutton = (Button) findViewById(R.id.exitbutton);
        Button randombutton = (Button) findViewById(R.id.randombutton);

        toHelloWorld.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, HelloWorld.class);
                startActivity(intent);
            }
        });

        randombutton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, random.class);
                startActivity(intent);
            }
        });

        exitbutton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                finish();
                System.exit(0);


            }
        });
    }
}