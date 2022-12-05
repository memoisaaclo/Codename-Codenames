package com.example.codenames;

/**
 * @author Dylan Booth
 */

import static com.example.codenames.utils.Const.URL_JSON_GETPLAYERS_FIRST;
import static com.example.codenames.utils.Const.URL_JSON_GETPLAYERS_SECOND;
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

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

public class spectatorLobby extends AppCompatActivity implements View.OnClickListener{

    private TextView lobby_name;
    private TextView error_text;
    private TextView player_count;
    private Button exit;
    private Button toGame;
    private LinearLayout pList;
    private String id;
    private String lobbyName;
    private JSONArray players;
    WebSocketClient cc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spectator_lobby);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        lobby_name = (TextView) findViewById(R.id.specLobby_name);
        lobbyName = intent.getStringExtra("lobbyName");
        lobby_name.setText(lobbyName);
        player_count = (TextView) findViewById(R.id.specLobby_text_playercount);
        pList = (LinearLayout) findViewById(R.id.specLobby_view);

        error_text = (TextView) findViewById(R.id.specLobby_error);

        exit = (Button) findViewById(R.id.specLobby_exit);
        exit.setOnClickListener(this);

        toGame = (Button) findViewById(R.id.specLobby_toGame);
        toGame.setOnClickListener(this);
        toGame.setVisibility(View.INVISIBLE);

        String w = "ws://10.90.75.56:8080/websocket/games/update/" + id;

        try {
            cc = new WebSocketClient(new URI(w)) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    getPlayers();
                    cc.send("update");
                }

                @Override
                public void onMessage(String s) {
                    if (s.toLowerCase(Locale.ROOT).equals("update")) {
                        System.out.println("This is the message: " + s);
                        getPlayers();
                    } else if (s.toLowerCase(Locale.ROOT).equals("start")) {
                        System.out.println("This is the message: " + s);
                        startActivity(new Intent(spectatorLobby.this, spectatorViewing.class).putExtra("id", id));
                    }
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    System.out.println("There was an issue and it closed");
                    System.out.println("The issue was " + s);
                }

                @Override
                public void onError(Exception e) {
                    System.out.println(e.toString());
                }
            };
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        cc.connect();

    }

    /**
     * Makes a GET request to get all players in lobby. Calls addPlayer() to list players in lobby.
     * Calls addPlayer with values returned by the request in the form of JSONObjects in a JSONArray
     */
    private void getPlayers() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = URL_JSON_GETPLAYERS_FIRST + id + URL_JSON_GETPLAYERS_SECOND;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            pList.removeAllViews();

                            JSONArray object = new JSONArray(response);
                            players = object;
                            player_count.setText(Integer.toString(players.length()));
                            System.out.println(response);
                            for (int i = 0; i < players.length(); i++) {
                                JSONObject o = (JSONObject) players.get(i);
                                String name = o.get("username").toString();
                                String role = o.get("role").toString();
                                String team = o.get("team").toString();
                                addPlayer(name, role, team);
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
     * Helper method that adds the players to list. Creates a horizontal LinearLayout called "row".
     * @param pName Sets the TextView value in the row with the player name
     * @param role Sets the second TextView in the row with the role of player
     * @param team Sets the background color of the "row" with the team of player
     */
    private void addPlayer(String pName, String role, String team) {

        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 150));

        LinearLayout.LayoutParams name = new LinearLayout.LayoutParams(500, 125);
        name.setMarginStart(100);
        name.setMargins(100, 20, 0, 20);
        LinearLayout.LayoutParams pRole = new LinearLayout.LayoutParams(500, 125);
        pRole.setMargins(0, 20, 150, 20);

        //Change background on row to player team
        if(team.toLowerCase(Locale.ROOT).equals("red")) {
            row.setBackgroundColor(Color.RED);
        } else if (team.toLowerCase(Locale.ROOT).equals("blue")) {
            row.setBackgroundColor(Color.BLUE);
        }

        //Create TextView to show player name
        TextView t = new TextView(this);
        t.setText(pName);
        t.setTextSize(20);
        t.setTextColor(Color.WHITE);
        t.setLayoutParams(name);


        row.addView(t);

        //Create TextView with role
        TextView r = new TextView(this);
        r.setText(role);
        r.setTextSize(20);
        r.setTextColor(Color.WHITE);
        r.setLayoutParams(pRole);

        row.addView(r);

        pList.addView(row);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.specLobby_exit) {
            startActivity(new Intent(spectatorLobby.this, spectatorHub.class));
        } else if (v.getId() == R.id.specLobby_toGame) {
            startActivity(new Intent(spectatorLobby.this, spectatorViewing.class).putExtra("lobbyName", lobbyName).putExtra("id", id));
        }
    }
}