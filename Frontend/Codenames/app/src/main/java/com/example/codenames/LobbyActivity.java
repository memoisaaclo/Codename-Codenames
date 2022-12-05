package com.example.codenames;

/**
 * @author Dylan Booth, James Driskell
 */
import static com.example.codenames.utils.Const.*;
import static java.lang.Thread.sleep;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.codenames.services.RequestListener;
import com.example.codenames.services.VolleyListener;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

public class LobbyActivity extends Activity implements View.OnClickListener
{
    private String TAG = LobbyActivity.class.getSimpleName();
    private TextView player_count;
    private TextView lobby_name;
    private TextView user;
    private TextView errortext;
    private Button exit;
    private String username;
    private String id;
    private String role;
    private String team;
    private String lobbyName;
    private JSONArray players;
    private LinearLayout pList;

    private Button rSpy;
    private Button rOps;
    private Button bSpy;
    private Button bOps;
    private Button toGame;

    WebSocketClient cc;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        player_count = (TextView) findViewById(R.id.text_playercount);

        //setting and saving username
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        user = (TextView) findViewById(R.id.lobby_username);
        user.setText(username);
        id = intent.getStringExtra("id");
        team = "RED";
        role = "OPERATIVE";
        lobby_name = (TextView) findViewById(R.id.text_header);
        lobbyName = intent.getStringExtra("lobbyName");
        lobby_name.setText(lobbyName);

        errortext = (TextView) findViewById(R.id.lobby_errormessage);

        pList = (LinearLayout) findViewById(R.id.player_list);

        exit = (Button) findViewById(R.id.reg_exit3);

        exit.setOnClickListener(this);

        //Setting role buttons
        rSpy = (Button) findViewById(R.id.button_red_spymaster);
        rOps = (Button) findViewById(R.id.button_red_operative);
        bSpy = (Button) findViewById(R.id.button_blue_spymaster);
        bOps = (Button) findViewById(R.id.button_blue_operative);
        toGame = (Button) findViewById(R.id.lobby_playGame);

        rSpy.setOnClickListener(this);
        rOps.setOnClickListener(this);
        bSpy.setOnClickListener(this);
        bOps.setOnClickListener(this);
        toGame.setOnClickListener(this);
        toGame.setVisibility(View.INVISIBLE);

        String w = "ws://10.90.75.56:8080/websocket/games/update/" + username;

        try {
            cc = new WebSocketClient(new URI(w)) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    getPlayers();
                    checkToStart(players);
                    cc.send("update");
                }

                @Override
                public void onMessage(String s) {
                    System.out.println("This is the message:" + s);
                    if (s.equals("update")) {
                        getPlayers();
                    } else if (s.equals("start")) {
                        //start the game, send everyone to game screen
                        if (role.equals("spymaster")) {
                            startActivity(new Intent(LobbyActivity.this, SpymasterGameActivity.class)
                                    .putExtra("username", username).putExtra("id", id).putExtra("team", team));
                        } else {
                            startActivity(new Intent(LobbyActivity.this, OperativeGameActivity.class)
                                    .putExtra("username", username).putExtra("id", id).putExtra("team", team));
                        }
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
     * Makes a GET request to get all roles, teams, and players. Calls helper method addPlayer() to add player to the active list
     * with values returned by the request.
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

        checkToStart(players);
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

        LinearLayout.LayoutParams name = new LinearLayout.LayoutParams(500, 150);
        name.setMargins(100, 20, 0, 20);
        LinearLayout.LayoutParams pRole = new LinearLayout.LayoutParams(500, 150);
        pRole.setMargins(0, 20, 100, 20);

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


    // All button click handlers
    @Override
    public void onClick(View view) {

        try {
            if (view.getId() == R.id.reg_exit3) {
                leaveLobby();
            }
            if (view.getId() == R.id.button_red_spymaster) {
                setPlayerTeam("red");
                setPlayerRole("spymaster");
                cc.send("update");
            } else if (view.getId() == R.id.button_red_operative) {
                setPlayerTeam("red");
                setPlayerRole("operative");
                cc.send("update");
            } else if (view.getId() == R.id.button_blue_spymaster) {
                setPlayerTeam("blue");
                setPlayerRole("spymaster");
                cc.send("update");
            } else if (view.getId() == R.id.button_blue_operative) {
                setPlayerTeam("blue");
                setPlayerRole("operative");
                cc.send("update");
            } else if (view.getId() == R.id.lobby_playGame) {
                cc.send("start");
                System.out.println("message sent");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Makes POST request to set the players role
     * @param role String value with the role the player selected
     * @throws JSONException Exception thrown when failed casting or key "message" does not exist
     */
    private void setPlayerRole(String role) throws JSONException {
        this.role = role;
        RequestListener roleListener = new RequestListener() {
            @Override
            public void onSuccess(Object response) throws JSONException {
                JSONObject object = new JSONObject((String) response);

                try {
                    if(object.get("message").equals("success")) {
                        //display success message
                    } else {
                        //display message ex. Player already has role spymaster
                        errortext.setText(object.get("message").toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                System.out.println(errorMessage);
            }
        };

        String url = URL_JSON_SETROLE_FIRST + username + URL_JSON_SETROLE_SECOND + role;

        VolleyListener.makeRequest(this, url, roleListener, Request.Method.POST);
    }

    /**
     * Makes POST request to change the players team to the color provided. Changes the rows background
     * @param team String value of the team the player wishes to join.
     * @throws JSONException Exception thrown when key "message" does not exist, or their is a casting error
     */
    private void setPlayerTeam(String team) throws JSONException {
        this.team = team;
        RequestListener teamListener = new RequestListener() {
            @Override
            public void onSuccess(Object response) throws JSONException {
                JSONObject object = new JSONObject((String) response);

                if (object.get("message").equals("success")) {
                    // display success message


                } else {
                    //display message ex. Cant join this team
                    errortext.setText(object.get("message").toString());
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                System.out.println(errorMessage);
            }
        };

        String url = URL_JSON_SETTEAM_FIRST + username + URL_JSON_SETTEAM_SECOND + team;

        VolleyListener.makeRequest(this, url, teamListener, Request.Method.POST);
    }

    /**
     * Makes DELETE request to remove player from the lobby. When exit button is clicked, request is sent and if successful, the player
     * will be returned to the HubActivity.
     * @throws JSONException Exception thrown when a casting error occurs, or there does not exist a key "message" in JSONObject
     */
    private void leaveLobby() throws JSONException {
        RequestListener leaveListener = new RequestListener() {
            @Override
            public void onSuccess(Object response) throws JSONException {
                System.out.println(response);
                JSONObject object = new JSONObject((String) response);
                if(object.get("message").equals("success")) {
                    //leave and go to hub
                    cc.close();
                    startActivity(new Intent(LobbyActivity.this, HubActivity.class).putExtra("username", username));
                } else {
                    //display error message
                    errortext.setText(object.get("message").toString());
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                System.out.println(errorMessage);
            }
        };

        String url = URL_JSON_REMOVEPLAYER_FIRST + id + URL_JSON_REMOVEPLAYER_SECOND + username;

        VolleyListener.makeRequest(this, url, leaveListener, Method.DELETE);
    }

    /**
     * Gets team and role information to determine if game is ready to start. If so, the toGame
     * button will appear and be able to be used to send the lobby to the game screen
     */
    private void checkToStart(JSONArray array) {
        //Ints for array position
        final int REDSPYMASTER = 0;
        final int REDOPERATIVE = 1;
        final int BLUESPYMASTER = 2;
        final int BLUEOPERATIVE = 3;
        //Array to hold counts
        int[] roleCount = {0, 0, 0, 0};

        for (int i = 0; i < players.length(); i++) {
            try {
                JSONObject o = (JSONObject) array.get(i);
                if(o.get("team").equals("RED")) {
                    if(o.get("role").equals("SPYMASTER")) {
                        roleCount[REDSPYMASTER]++;
                    } else if (o.get("role").equals("OPERATIVE")){
                        roleCount[REDOPERATIVE]++;
                    }
                } else {
                    if(o.get("role").equals("SPYMASTER")) {
                        roleCount[BLUESPYMASTER]++;
                    } else if (o.get("role").equals("OPERATIVE")) {
                        roleCount[BLUEOPERATIVE]++;
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (roleCount[REDSPYMASTER] == 1 && roleCount[REDOPERATIVE] >= 1 && roleCount[BLUESPYMASTER] == 1 && roleCount[BLUEOPERATIVE] >=1) {
            toGame.setVisibility(View.VISIBLE);
        }

        for(int i = 0; i < 4; i++) {
            System.out.println(roleCount[i] + " players");
        }

        if (roleCount[REDSPYMASTER] > 1 && roleCount[BLUESPYMASTER] > 1) {
            errortext.setText("Only 1 Spymaster per team");
        }


    }

}