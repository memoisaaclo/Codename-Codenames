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

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

public class spectatorViewing extends AppCompatActivity implements View.OnClickListener {

    private Button exit;
    private LinearLayout cardList;
    private String lobbyName;
    private String id;
    private TextView t;
    private TextView lName;
    private WebSocketClient cc;


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

        String w = "ws://10.90.75.56:8080/websocket/games/update/" + id;

        try {
            cc = new WebSocketClient(new URI(w)) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    getCards();
                    cc.send("update");
                }

                @Override
                public void onMessage(String s) {
                    System.out.println("This is the message:" + s);
                    getCards();
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

                            JSONObject obj = new JSONObject(response);
                            JSONArray object = new JSONArray((obj.get("cards")));

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

        if (color.toLowerCase(Locale.ROOT).equals("red") && isRevealed.toLowerCase(Locale.ROOT).equals("true")) {
            t.setBackgroundColor(Color.RED);
        } else if (color.toLowerCase(Locale.ROOT).equals("blue") && isRevealed.toLowerCase(Locale.ROOT).equals("true")) {
            t.setBackgroundColor(Color.BLUE);
        } else if (color.toLowerCase(Locale.ROOT).equals("grey")) {
            t.setBackgroundColor(Color.GRAY);
            t.setTextColor(Color.BLACK);
        } else if (color.toLowerCase(Locale.ROOT).equals("black") && isRevealed.toLowerCase(Locale.ROOT).equals("true")){
            t.setBackgroundColor(Color.BLACK);
        } else {

        }

        t.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        t.setLayoutParams(new LinearLayout.LayoutParams(210, 200));

        return t;
    }

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
        cc.close();
        startActivity(new Intent(spectatorViewing.this, spectatorHub.class));
    }
}