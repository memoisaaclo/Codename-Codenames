package com.example.codenames;

import static com.example.codenames.utils.Const.URL_JSON_LOBBY;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class spectatorHub extends AppCompatActivity implements View.OnClickListener {

    private Button exit; // Button to exit back to the menu screen
    private JSONArray lobbies; // JSONArray to hold the values of active lobbies globally within class
    private LinearLayout lobbyDisplay; // LinearLayout where the active lobbies will be displayed
    private LinearLayout.LayoutParams buttonLayout; // Parameters for buttons, size and margins
    private LinearLayout.LayoutParams textViewLayout; // Parameters for textViews, size and margins

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spectator_hub);

        exit = (Button) findViewById(R.id.specHub_exit6);

        exit.setOnClickListener(this);

        lobbyDisplay = (LinearLayout) findViewById(R.id.spec_view);

        buttonLayout = new LinearLayout.LayoutParams(650,150);
        buttonLayout.setMarginStart(20);
        textViewLayout = new LinearLayout.LayoutParams(300,150);
        textViewLayout.setMarginStart(100);

        getLobbies();

    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(spectatorHub.this, menu.class));
    }


    /*
    Helper method that takes a @param of a lobby object. Creates a LinearLayour row, then adds a button, and a textview to display
    lobby information and allow the spectator to see the games lobby details like players, roles, and teams.
     */
    private void addLobbies(lobby addLobby) {

        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        //Creating button
        Button a = new Button(this);
        a.setText(addLobby.getName());
        a.setLayoutParams(buttonLayout);
        a.setTextSize(20);
        //adding to row
        row.addView(a);

        a.setOnClickListener(new ClickListener(addLobby.getName(), addLobby.getId()));

        //Create TextView to show numPlayers
        TextView t = new TextView(this);
        t.setText(addLobby.getNumPlayers() + "/12");
        t.setTextSize(20);
        t.setTextColor(Color.BLACK);
        t.setLayoutParams(textViewLayout);

        //adding to row
        row.addView(t);

        lobbyDisplay.addView(row);

    }

    /*
    Inner-class to allow for the creation of buttons that will allow for the creation of as many lobbies as needed.
    Takes @params lobbyName, and the lobby id, which can be obtained from the backend upon loading of all lobbies.
    Sets the onClickListener.
     */
    class ClickListener implements View.OnClickListener {
        private String lobby;
        private String id;

        public ClickListener(String lobbyName, String id){
            this.lobby = lobbyName;
            this.id = id;
        }

        @Override
        public void onClick(View v) {
            startActivity(new Intent(spectatorHub.this, spectatorLobby.class).putExtra("lobbyName", lobby).putExtra("id", id));
        }
    }


    /*
    Method to display all active lobbies in the scrollView. Makes JSON GET request to get active lobby info like, name and id.
    Calls the helper method addLobbies() with a lobby @param which is called also.
     */
    private void getLobbies() {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, URL_JSON_LOBBY,
                new Response.Listener<String> () {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray object = new JSONArray(response);
                            lobbies = object;
                            for (int i = 0; i < lobbies.length(); i++) {
                                JSONObject o = (JSONObject) lobbies.get(i);
                                String name = o.get("lobbyName").toString();
                                int numPlayer = (int) o.get("numPlayers");
                                String id = o.get("identity").toString();
                                addLobbies(new lobby(name, numPlayer, id));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
}