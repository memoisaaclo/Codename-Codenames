package com.example.jimmy.buttons;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    Button b1, b2;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1=(Button)findViewById(R.id.but1);
        b2=(Button)findViewById(R.id.but2);
        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, home.class);
                startActivity(i);
            }
        });
        b2.setOnClickListener(new View.On.ClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(getApplicationContext(),"hello button2",Toast.LENGTH_LONG).show();
            }
        });
    }
}