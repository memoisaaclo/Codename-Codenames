package com.example.codenames;

import static com.example.codenames.utils.Const.URL_JSON_REMOVEPLAYER_FIRST;
import static com.example.codenames.utils.Const.URL_JSON_REMOVEPLAYER_SECOND;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.codenames.services.RequestListener;
import com.example.codenames.services.VolleyListener;

import org.json.JSONException;
import org.json.JSONObject;

public class winScreen extends AppCompatActivity implements View.OnClickListener {

    String username;
    String id;
    Button toHub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_screen);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        String message = intent.getStringExtra("message");
        id = intent.getStringExtra("id");

        TextView display = (TextView) findViewById(R.id.win_message);
        TextView user = (TextView) findViewById(R.id.win_username);

        display.setText(message);
        user.setText(username);

        toHub = (Button) findViewById(R.id.win_toHub);
        toHub.setOnClickListener(this);


    }

    /**
     * Removes current player from lobby
     * Uses "username" to let the game know which user to remove from the list of users in game
     * @throws JSONException Exception thrown if Casting error to JSONObject or if key "message" does not exist in JSONObject
     */
    private void leaveLobby() throws JSONException {
        RequestListener leaveListener = new RequestListener() {
            @Override
            public void onSuccess(Object response) throws JSONException {
                System.out.println(response);
                JSONObject object = new JSONObject((String) response);
                if(object.get("message").equals("success")) {
                    //leave and go to hub
                    startActivity(new Intent(winScreen.this, HubActivity.class).putExtra("username", username));
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                System.out.println(errorMessage);
            }
        };

        String url = URL_JSON_REMOVEPLAYER_FIRST + id + URL_JSON_REMOVEPLAYER_SECOND + username;

        VolleyListener.makeRequest(this, url, leaveListener, Request.Method.DELETE);
    }

    @Override
    public void onClick(View v) {
        try {
            leaveLobby();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}