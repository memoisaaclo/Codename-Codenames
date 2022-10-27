package com.example.codenames;

import static com.example.codenames.utils.Const.URL_JSON_LOBBY;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

    Button exit;
    JSONArray lobbies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spectator_hub);

        exit = (Button) findViewById(R.id.specHub_exit6);

        exit.setOnClickListener(this);

        getLobbys();

    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(spectatorHub.this, menu.class));
    }

    private void addLobbies(Object lobbys) {
//        try {
//            //add lobbys
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    private void getLobbys() {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, URL_JSON_LOBBY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            lobbies = object.getJSONArray("l");
                            for(int i = 0; i < lobbies.length(); i++) {
                                System.out.println(lobbies.get(i));
                                addLobbies(lobbies.get(i));
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