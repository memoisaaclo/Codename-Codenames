package com.example.codenames;

import static com.example.codenames.utils.Const.URL_JSON_CREATE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.codenames.services.RequestListener;
import com.example.codenames.services.VolleyListener;

import org.json.JSONException;
import org.json.JSONObject;

public class HubActivity extends AppCompatActivity implements OnClickListener
{
    private Button btnLobby1,btnExit;
    private Button create;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);

        btnLobby1 = (Button) findViewById(R.id.button_lobby1);
        btnExit = (Button) findViewById(R.id.reg_exit6);
        create = (Button) findViewById(R.id.createLobby);

        btnLobby1.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        create.setOnClickListener(this);

        //Getting username and any other info that may need sent from previous activity
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        //Setting username textview
        ((TextView) findViewById(R.id.hub_username)).setText(username);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button_lobby1:
                startActivity(new Intent(HubActivity.this, LobbyActivity.class).putExtra("username", username));
                break;
            case R.id.reg_exit6:
                startActivity(new Intent(HubActivity.this, menu.class).putExtra("username", username));
                break;
            case R.id.createLobby:
                startActivity(new Intent(HubActivity.this, createLobby.class).putExtra("username", username));
                break;
            default:
                break;
        }
    }



}