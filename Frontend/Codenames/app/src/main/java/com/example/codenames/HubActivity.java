package com.example.codenames;

import static com.example.codenames.utils.Const.URL_JSON_LOBBY;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
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
        buttonLayout = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        textViewLayout = new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT);

        getLobbies();

//        try {
//            Thread.sleep(20000);
//            startActivity(new Intent(HubActivity.this, HubActivity.class).putExtra("username", username));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
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

    private void addLobbies(lobby addLobby, int lobbyNum) {

        //Creating button
        Button a = new Button(this);
        a.setText(addLobby.getName());
        a.setLayoutParams(buttonLayout);
        a.setId(lobbyNum);

        a.setOnClickListener(joinListener);

        //Create TextView to show numPlayers
        TextView t = new TextView(this);
        t.setText(addLobby.getNumPlayers() + "/12");
        a.setLayoutParams(textViewLayout);

    }

    private View.OnClickListener joinListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(HubActivity.this, LobbyActivity.class).putExtra("username", username));
        }
    };

    private void getLobbies() {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, URL_JSON_LOBBY,
                new Response.Listener<String> () {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray object = new JSONArray(response);
                            lobbies = object;
                            for (int i = 1; i < lobbies.length() - 1; i++) {
                                JSONObject o = (JSONObject) lobbies.get(i);
                                String name = o.get("lobbyName").toString();
                                System.out.println(name);
//                                int numPlayer = 1;
                                int numPlayer = (int) o.get("numPlayers");
                                System.out.println(numPlayer);
                                addLobbies(new lobby(name, numPlayer), i);
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