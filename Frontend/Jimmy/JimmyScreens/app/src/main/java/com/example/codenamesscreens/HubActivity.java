package com.example.codenamesscreens;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HubActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);

        Button btnNavToLobby1 = (Button) findViewById(R.id.button_lobby1);

        btnNavToLobby1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intent = new Intent(HubActivity.this, LobbyActivity.class);
                startActivity(intent);
            }
        });
    }
}