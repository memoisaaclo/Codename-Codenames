package com.example.codenames;

import static com.example.codenames.utils.Const.URL_JSON_CREATE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.codenames.services.RequestListener;
import com.example.codenames.services.VolleyListener;

import org.json.JSONException;
import org.json.JSONObject;

public class createLobby extends AppCompatActivity {

    private Button create;
    private EditText name;
    private String username;
    private String lobbyName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lobby);

        create = (Button) findViewById(R.id.create_create);
        name = (EditText) findViewById(R.id.create_name);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        ((TextView) findViewById(R.id.create_username)).setText(username);

        create.setOnClickListener(nameListener);
    }

    private View.OnClickListener nameListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            lobbyName = name.getText().toString();
            try {
                sendLobbyName(lobbyName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };



    private void sendLobbyName(String name) throws JSONException {
        RequestListener lobbyListener = new RequestListener() {
            @Override
            public void onSuccess(Object response) {
                JSONObject object = (JSONObject) response;
                System.out.println(object.toString());
                Intent next = new Intent(createLobby.this, LobbyActivity.class);
                next.putExtra("username", username);
                //remind isaac to make the player join lobby that is create

                try {
                    if(object.get("message").equals("success")) {
                        startActivity(next);
                    } else {
                        ((TextView) findViewById(R.id.create_error)).setText(object.get("message").toString());
                    }
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {
                System.out.println("error");
                System.out.println(error);
            }
        };

        JSONObject data = new JSONObject();
        data.put("gameLobbyName", name);

        VolleyListener.makeRequest(this, URL_JSON_CREATE, lobbyListener, data, Request.Method.POST);
    }
}