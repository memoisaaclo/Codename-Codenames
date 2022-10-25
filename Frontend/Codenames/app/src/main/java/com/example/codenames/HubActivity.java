package com.example.codenames;

import static com.example.codenames.utils.Const.URL_JSON_CREATE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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
    private PopupWindow pw;

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
                popup();
                break;
            default:
                break;
        }
    }

    private void popup() {
        LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popUp = inflater.inflate(R.layout.create_lobby, null);
        pw = new PopupWindow(popUp, 750, 500, true);

        Button createLobby = (Button) popUp.findViewById(R.id.create_create);

        createLobby.setOnClickListener(createListener);
    }

    private View.OnClickListener createListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText name = (EditText) v.findViewById(R.id.lobbyName);
            try {
                sendLobbyName(name.getText().toString());
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
                Intent next = new Intent(HubActivity.this, LobbyActivity.class);
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