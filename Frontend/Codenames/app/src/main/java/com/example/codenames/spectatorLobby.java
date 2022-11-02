package com.example.codenames;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class spectatorLobby extends AppCompatActivity implements View.OnClickListener{

    private TextView lobby_name;
    private TextView error_text;
    private TextView player_count;
    private Button exit;
    private LinearLayout pList;
    private String id;
    private String lobbyName;
    private JSONArray players;



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
        name.setMarginStart(100);
        LinearLayout.LayoutParams pRole = new LinearLayout.LayoutParams(500, 150);
        pRole.setMarginEnd(100);

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
        t.setTextColor(Color.BLACK);
        t.setLayoutParams(name);

        row.addView(t);

        //Create TextView with role
        TextView r = new TextView(this);
        r.setText(role);
        r.setTextSize(20);
        r.setTextColor(Color.BLACK);
        r.setLayoutParams(pRole);

        row.addView(r);

        pList.addView(row);
    }

    @Override
    public void onClick(View v) {

    }
}