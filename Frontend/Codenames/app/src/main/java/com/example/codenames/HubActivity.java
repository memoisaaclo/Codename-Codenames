package com.example.codenames;

/**
 * @author Dylan Booth & James Driskell
 */

import static com.example.codenames.utils.Const.*;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.codenames.services.RequestListener;
import com.example.codenames.services.VolleyListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HubActivity extends AppCompatActivity implements OnClickListener {

    private Button btnExit; // Button to exit player back to menu
    private Button create; // Button to forward player to the createLobby screen
    private String username; // String to hold the players username, used when transitioning between screens
    private JSONArray lobbies; // JSONArray to hold the current available lobbies
    private LinearLayout lobbyDisplay; // LinearLayout with vertical orientation to add the lobbies buttons and textviews

    private LinearLayout.LayoutParams buttonLayout; // LayoutParams for the buttons listed for lobbies
    private LinearLayout.LayoutParams textViewLayout; // LayoutParams for the textviews listed for lobbies

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);

        btnExit = (Button) findViewById(R.id.reg_exit6);
        create = (Button) findViewById(R.id.createLobby);

        btnExit.setOnClickListener(this);
        create.setOnClickListener(this);

        //Getting username and any other info that may need sent from previous activity
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        //Setting username textview
        ((TextView) findViewById(R.id.hub_username)).setText(username);

        lobbyDisplay = (LinearLayout) findViewById(R.id.hub_list);

        buttonLayout = new LinearLayout.LayoutParams(650,150);
        buttonLayout.setMarginStart(20);
        textViewLayout = new LinearLayout.LayoutParams(300,150);
        textViewLayout.setMarginStart(100);

        getLobbies();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
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

    /**
     * Helper method that makes a LinearLayout row, that contains a button and a textview to display lobby information.
     * @param addLobby lobby class that contains the lobby name, id, and num players
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

    /**
     * Inner-class to allow for the creation of buttons that will allow for the creation of as many lobbies as needed.
     * Takes @params lobbyName, and the lobby id, which can be obtained from the backend upon loading of all lobbies.
     * Sets the onClickListener.
     */
    class ClickListener implements OnClickListener {
        private String lobby;
        private String id;

        public ClickListener(String lobbyName, String id){
            this.lobby = lobbyName;
            this.id = id;
        }

        @Override
        public void onClick(View v) {
            try {
                if (username != null) {
                    addPlayer(lobby, id);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method do display all active lobbies in the scrollView, makes a GET request to get active lobbies information. Calls
     * the helper method addLobbies() to set values and create the new row in the list.
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

    /**
     * Makes POST reqeust to add player to lobby. Request is sent with players username and parameters. When added to
     * lobby, the are set with default role/team red operative
     * @param lobby String value of the name of the lobby that the player is to be added to
     * @param id String value of the name of the lobby id that the player is to be added to
     * @throws JSONException Exception thrown when casting error occurs, or key is not found in JSONObject
     */
    private void addPlayer (String lobby, String id) throws JSONException {
        RequestListener addListener = new RequestListener() {
            @Override
            public void onSuccess(Object response) throws JSONException {

                JSONObject object = (JSONObject) response;

                try {
                    if (object.get("message").equals("success")) {
                        startActivity(new Intent(HubActivity.this, LobbyActivity.class)
                                .putExtra("username", username).putExtra("lobbyName", lobby).putExtra("id", id));
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