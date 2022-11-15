package com.example.codenames;

/**
 * @author Dylan Booth
 */

import static com.example.codenames.utils.Const.*;
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

public class spectatorViewing extends AppCompatActivity implements View.OnClickListener {

    private Button exit; // Button to exit back to SpectatorHub
    private LinearLayout cardList; // LinearLayout to hold and display the current cards in game
    private String lobbyName; // String to hold the lobby name, used to set lobby name on screen
    private String id; // String to hold id value, used to get cards/score, will be used to refresh screen
    private TextView lName; // TextView to display lobby name


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spectator_viewing);

        exit = (Button) findViewById(R.id.specGame_exit);
        exit.setOnClickListener(this);

        Intent intent = getIntent();
        lobbyName = intent.getStringExtra("lobbyName");
        id = intent.getStringExtra("id");

        // Setting LobbyName
        lName = (TextView) findViewById(R.id.specGame_lobbyName);
        lName.setText(lobbyName);

        // Getting linearlayout
        cardList = (LinearLayout) findViewById(R.id.specGame_cardView);

        System.out.println(id);

        getCards();

    }

    /**
     * Makes a GET request to get all cards to be displayed. When called, will display words and colors. Can be changed to only show color of cards
     * that have been revealed. Calls getTextView to create the TextView to be displayed. Then adds the TextView to a horizontal LinearLayout
     * row.
     */
    private void getCards() {

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = URL_JSON_GETALLCARDS_SPECTATOR_FIRST + id + URL_JSON_GETALLCARDS_SPECTATOR_SECOND;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            System.out.println(response);

                            JSONArray object = new JSONArray(response);
                            for (int i = 0; i < 5; i++) {
                                LinearLayout row = addRow();

                                for (int j = i*5; j < (i*5) + 5; j++) {
                                    JSONObject o = (JSONObject) object.get(j);
                                    row.addView(getTextView(o.get("word").toString(), o.get("color").toString(), o.get("revealed").toString()));

                                }

                                cardList.addView(row);
                            }

                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
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
    Helper method to create a TextView to display words and color. Can be configured to only display color if the card is Revealed.
    Returns a TextView t, with given information.
     */

    /**
     * Helper method to create a TextView to display words and color. Can be configured to only display color if the card isRevealed.
     * @param word String value with the word value of the card
     * @param color String value with the color value of the card
     * @param isRevealed String value of whether or not the card isRevealed
     * @return TextView t setup with given parameters
     */
    private TextView getTextView(String word, String color, String isRevealed) {

        TextView t = new TextView(this);
        t.setText(word);
        t.setTextSize(22);
        t.setTextColor(Color.WHITE);

        if (color.toLowerCase(Locale.ROOT).equals("red")) {
            t.setBackgroundColor(Color.RED);
        } else if (color.toLowerCase(Locale.ROOT).equals("blue")) {
            t.setBackgroundColor(Color.BLUE);
        } else if (color.toLowerCase(Locale.ROOT).equals("grey")) {
            t.setBackgroundColor(Color.GRAY);
            t.setTextColor(Color.BLACK);
        } else {
            t.setBackgroundColor(Color.BLACK);
        }

        t.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        t.setLayoutParams(new LinearLayout.LayoutParams(210, 200));

        return t;
    }

    /*
    Helper method to create a new row to be displayed in cardList. Will return a horizontal LinearLayout for
    5 cards to be held and displayed.
     */

    /**
     * Helper method to create the row to hold 5 card values.
     * @return LinearLayout that is horizontal
     */
    private LinearLayout addRow() {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                200));

        return row;
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(spectatorViewing.this, spectatorHub.class));
    }
}