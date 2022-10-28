package com.example.codenames;

import static com.example.codenames.utils.Const.URL_JSON_LOBBY;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HubActivity extends AppCompatActivity implements OnClickListener {

    private Button btnLobby1,btnExit;
    private Button create;
    private String username;
    private JSONArray lobbies;
    private LinearLayout lobbyDisplay;

    private LinearLayout.LayoutParams buttonLayout;
    private LinearLayout.LayoutParams textViewLayout;

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
            case R.id.button_lobby1:
                startActivity(new Intent(HubActivity.this, LobbyActivity.class).putExtra("username", username));
                break;
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

        a.setOnClickListener(new ClickListener(addLobby.getName()));

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

    class ClickListener implements OnClickListener {
        private String lobby;

        public ClickListener(String lobbyName){
            this.lobby = lobbyName;
        }

        @Override
        public void onClick(View v) {
            startActivity(new Intent(HubActivity.this, LobbyActivity.class).putExtra("username", username).putExtra("lobbyName", lobby));
        }
    }

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
                                addLobbies(new lobby(name, numPlayer));
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