package com.example.codenames;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HubActivity extends AppCompatActivity implements OnClickListener
{
    private Button btnLobby1,btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);

        Button btnLobby1 = (Button) findViewById(R.id.button_lobby1);
        Button btnExit = (Button) findViewById(R.id.reg_exit6);

        btnLobby1.setOnClickListener(this);
        btnExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button_lobby1:
                startActivity(new Intent(HubActivity.this, LobbyActivity.class));
                break;
            case R.id.reg_exit6:
                startActivity(new Intent(HubActivity.this, menu.class));
                break;
            default:
                break;
        }
    }
}