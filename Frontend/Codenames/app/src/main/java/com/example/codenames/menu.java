package com.example.codenames;

import static com.example.codenames.utils.Const.URL_JSON_LOBBY;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import static com.example.codenames.utils.Const.*;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class menu extends AppCompatActivity implements View.OnClickListener{

    private Button play;
    private Button login;
    private Button info;
    private Button exit;
    private Button adminWords;
    private Button adminUsers;
    private TextView menuUser;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();

        menuUser = (TextView) findViewById(R.id.menu_username);
        username = intent.getStringExtra("username");
        menuUser.setText(username);

        //buttons
        play = (Button) findViewById(R.id.menu_play);
        login = (Button) findViewById(R.id.menu_login);
        info = (Button) findViewById(R.id.menu_user);
        exit = (Button) findViewById(R.id.menu_exit);
        adminWords = (Button) findViewById(R.id.menu_admin_words);
        adminUsers = (Button) findViewById(R.id.menu_admin_users);

        //button click listener
        play.setOnClickListener(this);
        login.setOnClickListener(this);
        info.setOnClickListener(this);
        exit.setOnClickListener(this);
        adminWords.setOnClickListener(this);
        adminUsers.setOnClickListener(this);


        if (username == null) {
            info.setVisibility(View.GONE);
            play.setText("Spectate");
        } else {
            login.setText("Logout");
            checkIfAdmin();
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.menu_login) {
            if (username == null) {
                //
                startActivity(new Intent(menu.this, login.class));
            } else {
                startActivity(new Intent(menu.this, menu.class));
            }
        } else if (v.getId() == R.id.menu_play) {
            // play game
            if (username != null) {
                //go to game hub
                startActivity(new Intent(menu.this, HubActivity.class).putExtra("username", username));
            } else if (username == null) {
                //go to spectator hub
//                startActivity(new Intent(menu.this, spectatorViewing.class).putExtra("id", "34"));
                startActivity(new Intent(menu.this, spectatorHub.class));
            }
        } else if (v.getId() == R.id.menu_user) {
            // user info connection

            startActivity(new Intent(menu.this, userInfo.class).putExtra("username", username));
        } else if (v.getId() == R.id.menu_exit) {
            //exit app
            System.exit(0);
        } else if (v.getId() == R.id.menu_admin_words) {
            //go to admin words
            startActivity(new Intent(menu.this, AdminWordsActivity.class).putExtra("username", username));
        } else if (v.getId() == R.id.menu_admin_users) {
            //go to admin users
            startActivity(new Intent(menu.this, AdminUsersActivity.class).putExtra("username", username));
        }
        else if (v.getId() == R.id.button_words) {
            startActivity(new Intent(menu.this, AdminWordsActivity.class));
        }
        else if (v.getId() == R.id.button_users) {
            startActivity(new Intent(menu.this, AdminUsersActivity.class));
        }
    }

    private void checkIfAdmin() {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, URL_JSON_ISADMIN + username,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);

                            if (object.get("message").equals("success")) {
                                adminWords.setVisibility(View.VISIBLE);
                                adminUsers.setVisibility(View.VISIBLE);
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