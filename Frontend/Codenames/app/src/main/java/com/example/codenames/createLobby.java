package com.example.codenames;

import static com.example.codenames.utils.Const.URL_JSON_CREATE;
import static com.example.codenames.utils.Const.URL_JSON_GENCARDS_FIRST;
import static com.example.codenames.utils.Const.URL_JSON_GENCARDS_SECOND;
import static com.example.codenames.utils.Const.URL_JSON_PLAYERNUM_POST_FIRST;
import static com.example.codenames.utils.Const.URL_JSON_PLAYERNUM_POST_SECOND;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.codenames.services.RequestListener;
import com.example.codenames.services.VolleyListener;

import org.json.JSONException;
import org.json.JSONObject;

public class createLobby extends AppCompatActivity implements View.OnClickListener {

    private Button create; // Button to call method to make reqeust to create lobby
    private Button back; // Button to return user to HubActivity
    private EditText name; // EditText to get value of lobby name
    private String username; // String to hold value of username, can be sent when transitioning screens
    private String lobbyName; // String to hold value of lobby name, can be sent when transitioning screens

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lobby);

        create = (Button) findViewById(R.id.create_create);
        back = (Button) findViewById(R.id.create_exit);
        name = (EditText) findViewById(R.id.create_name);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        ((TextView) findViewById(R.id.create_username)).setText(username);

        create.setOnClickListener(nameListener);
        back.setOnClickListener(this);
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


    /*
    Makes a POST request to create a lobby with the given lobby name. If the lobby is created successfully
    calls another method genCards() to create the card/word set for the new lobby.
     */
    private void sendLobbyName(String name) throws JSONException {
        RequestListener lobbyListener = new RequestListener() {
            @Override
            public void onSuccess(Object response) {
                JSONObject object = (JSONObject) response;
                System.out.println(object.toString());
                Intent next = new Intent(createLobby.this, LobbyActivity.class);
                next.putExtra("username", username);

                try {
                    if(object.get("message").equals("success")) {
                        next.putExtra("identity", object.get("id").toString());
                        next.putExtra("lobbyName", lobbyName);
                        addPlayer(username, object.get("id").toString());
                        genCards(object.get("id").toString());
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.create_exit) {
            startActivity(new Intent(createLobby.this, HubActivity.class).putExtra("username", username));
        }
    }

    /*
    Called by sendLobbyName(), when called the method makes a GET request to generate the cards for the given lobby.
    Takes @param id specify which lobby will have the cards generated for.
     */
    private void genCards (String id) throws JSONException {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, URL_JSON_GENCARDS_FIRST + id + URL_JSON_GENCARDS_SECOND,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                    }
                });
        queue.add(request);
    }

    /*
    Called by sendLobbyName(), when called makes a new POST request to add player to the lobby with
    @params lobby name, and id of the lobby.
     */
    private void addPlayer (String lobby, String id) throws JSONException {
        RequestListener addListener = new RequestListener() {
            @Override
            public void onSuccess(Object response) throws JSONException {

                JSONObject object = (JSONObject) response;

                try {
                    if (object.get("message").equals("success")) {
//                        startActivity(new Intent(createLobby.this, LobbyActivity.class)
//                                .putExtra("username", username).putExtra("lobbyName", lobby).putExtra("id", id));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                System.out.println("error");
                System.out.println(errorMessage);
            }
        };

        JSONObject data = new JSONObject();

        String url = URL_JSON_PLAYERNUM_POST_FIRST + id + URL_JSON_PLAYERNUM_POST_SECOND + username;
        VolleyListener.makeRequest(this, url, addListener, data, Request.Method.POST);
    }
}