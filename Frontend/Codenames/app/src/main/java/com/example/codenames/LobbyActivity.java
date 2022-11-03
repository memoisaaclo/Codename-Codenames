package com.example.codenames;

import static com.example.codenames.utils.Const.*;
import static java.lang.Thread.sleep;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.codenames.app.AppController;
import com.example.codenames.services.RequestListener;
import com.example.codenames.services.VolleyListener;
import com.example.codenames.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
    private String lobbyName;
    private JSONArray players;
    private LinearLayout pList;

    private Button rSpy;
    private Button rOps;
    private Button bSpy;
    private Button bOps;


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

        rSpy.setOnClickListener(this);
        rOps.setOnClickListener(this);
        bSpy.setOnClickListener(this);
        bOps.setOnClickListener(this);

//        postJsonObj();
        try
        {
            sleep(100);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        getPlayers();
    }

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
    }


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
                startActivity(new Intent(LobbyActivity.this, HubActivity.class).putExtra("username", username));
            }
            if (view.getId() == R.id.button_red_spymaster) {
                setPlayerTeam("red");
                setPlayerRole("spymaster");
            } else if (view.getId() == R.id.button_red_operative) {
                setPlayerTeam("red");
                setPlayerRole("operative");
            } else if (view.getId() == R.id.button_blue_spymaster) {
                setPlayerTeam("blue");
                setPlayerRole("spymaster");
            } else if (view.getId() == R.id.button_blue_operative) {
                setPlayerTeam("blue");
                setPlayerRole("operative");
            }

            getPlayers();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Sets player roles (Operative/Spymaster)
    private void setPlayerRole(String role) throws JSONException {
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

    // Sets Players team (Red/Blue)
    private void setPlayerTeam(String team) throws JSONException {
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

    // Removes player from lobby
    private void leaveLobby() throws JSONException {
        RequestListener leaveListener = new RequestListener() {
            @Override
            public void onSuccess(Object response) throws JSONException {
                System.out.println(response);
                JSONObject object = new JSONObject((String) response);
                if(object.get("message").equals("success")) {
                    //leave and go to hub
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

}