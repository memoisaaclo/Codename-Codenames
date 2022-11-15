package com.example.codenames;

/**
 * @author Dylan Booth
 */

import static com.example.codenames.utils.Const.URL_JSON_STATISTICS;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class userInfo extends AppCompatActivity implements View.OnClickListener {

    private Button exit; // Button to exit back to menu
    private TextView gamesWon; // TextView to display how many games the player has won
    private TextView gamesPlayed; // TextView to display how many games the player has played
    private TextView guessesMade; // TextView to display the amount of guesses made
    private TextView cluesGiven; // TextView to display the amount of clus the player has given
    private TextView correctGuesses; // TextView to display the amount of correct guesses player has made
    private TextView logins; // TextView to display number of times player has logged in
    private String username; // String to hold the players username


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        exit = (Button) findViewById(R.id.userInfo_exit);

        exit.setOnClickListener(this);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        ((TextView) findViewById(R.id.info_username)).setText(username);

        //TextView Initialization for the statistics
        gamesWon = (TextView) findViewById(R.id.info_stat_won);
        gamesPlayed = (TextView) findViewById(R.id.info_stat_played);
        guessesMade = (TextView) findViewById(R.id.info_stat_guesses);
        cluesGiven = (TextView) findViewById(R.id.info_stat_clues);
        correctGuesses = (TextView) findViewById(R.id.info_stat_correct);
        logins = (TextView) findViewById(R.id.info_stat_login);

        getUserInfo();

    }

    /**
     * Helper method to set user statistics with appropriate data. Called by getUserInfo()
     * @param stats JSONObject that contains the value pairs of the user statistics
     */
    private void updateInfo(JSONObject stats) {
        try {
            gamesWon.setText(stats.get("gamesWon").toString());
            gamesPlayed.setText(stats.get("gamesPlayed").toString());
            guessesMade.setText(stats.get("guessesMade").toString());
            cluesGiven.setText(stats.get("cluesGiven").toString());
            correctGuesses.setText(stats.get("correctGuesses").toString());
            logins.setText(stats.get("loginCount").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.userInfo_exit) {
            startActivity(new Intent(userInfo.this, menu.class).putExtra("username", username));
        }
    }

    /**
     * Method that makes a GET request to get the current users information. Calls updateInfo() with the response
     * to update user statistics.
     */
    private void getUserInfo() {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, URL_JSON_STATISTICS+username,
                new Response.Listener<String> () {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            updateInfo(object);
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