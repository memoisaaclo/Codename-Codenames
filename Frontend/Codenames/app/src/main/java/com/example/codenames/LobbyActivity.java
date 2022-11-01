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
import com.example.codenames.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class LobbyActivity extends Activity implements View.OnClickListener
{
    private String TAG = LobbyActivity.class.getSimpleName();
    private TextView player_count;
    private TextView lobby_name;
    private TextView user;
    private Button to_lobby;
    private String username;
    private String id;
    private String lobbyName;
    private JSONArray players;


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

        System.out.println("ID: " + id);

        to_lobby = (Button) findViewById(R.id.reg_exit3);

        to_lobby.setOnClickListener(this);

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
//        makeJsonObjReq(); //adds player to lobby
    }

    private void getPlayers() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = URL_JSON_GETPLAYERS_FIRST + id + URL_JSON_GETPLAYERS_SECOND;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray object = new JSONArray(response);
                            players = object;
                            player_count.setText(Integer.toString(players.length()));
                            for (int i = 0; i < players.length(); i++) {
                                JSONObject o = (JSONObject) players.get(i);
                                String name = o.get("username").toString();

                                addPlayer(name);
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

    private void addPlayer(String pName) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        LinearLayout.LayoutParams textViewLayout = new LinearLayout.LayoutParams(950, 150);
        textViewLayout.setMarginStart(100);

        //Create TextView to show player name
        TextView t = new TextView(this);
        t.setText(pName);
        t.setTextSize(20);
        t.setTextColor(Color.BLACK);
        t.setLayoutParams(textViewLayout);

        row.addView(t);


    }

    /**
     * Making json object request
     * */
//    private void makeJsonObjReq()
//    {
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
//                Const.URL_JSON_PLAYERNUM_GET, null,
//                new Response.Listener<JSONObject>()
//                {
//                    @Override
//                    public void onResponse(JSONObject response)
//                    {
//                        try
//                        {
//                            Log.d(TAG, response.getString("playerNum"));
//                            player_count = findViewById(R.id.text_playercount);
//                            player_count.setText(response.getString("playerNum")); //display string
//                        }
//                        catch (JSONException e)
//                        {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener()
//        {
//            @Override
//            public void onErrorResponse(VolleyError error)
//            {
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
//            }
//        })
//        {
//
//            /**
//             * Passing some request headers
//             * */
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError
//            {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json");
//                return headers;
//            }
//
//            @Override
//            protected Map<String, String> getParams()
//            {
//                Map<String, String> params = new HashMap<String, String>();
////                params.put("name", "Androidhive");
////                params.put("email", "abc@androidhive.info");
////                params.put("pass", "password123");
//
//                return params;
//            }
//        };
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
//
//        // Cancelling request
//        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
//    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.reg_exit3) {
            startActivity(new Intent(LobbyActivity.this, HubActivity.class).putExtra("username", username));
        }
    }
}