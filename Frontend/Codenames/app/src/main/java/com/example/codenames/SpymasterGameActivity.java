package com.example.codenames;

/**
 * @author Jimmy Driskell
 */

import static com.example.codenames.utils.Const.URL_JSON_WORD_ADD;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.CompoundButtonCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
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
import static com.example.codenames.utils.Const.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.java_websocket.client.WebSocketClient;
//import org.java_websocket.drafts.Draft;
//import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class SpymasterGameActivity extends AppCompatActivity implements View.OnClickListener
{
    private String TAG = SpymasterGameActivity.class.getSimpleName();
    private Button btnExit;
    private Button btnSendClue;
    private TextView card_name;
    private String input;
    private String lobbyID;
    private EditText text_edit;
    private String username;
    private WebSocketClient dd;
    private SeekBar seekNumGuesses;
    private TextView textNumGuesses;
    private TextView red_score;
    private TextView blue_score;
    private JSONObject red_score_object;
    private JSONObject blue_score_object;
    private String team;
    private Toolbar header;

    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";

    private Button cards[] = new Button[25];

    private static final int[] CARD_IDS = {
        R.id.button_card1,
        R.id.button_card2,
        R.id.button_card3,
        R.id.button_card4,
        R.id.button_card5,
        R.id.button_card6,
        R.id.button_card7,
        R.id.button_card8,
        R.id.button_card9,
        R.id.button_card10,
        R.id.button_card11,
        R.id.button_card12,
        R.id.button_card13,
        R.id.button_card14,
        R.id.button_card15,
        R.id.button_card16,
        R.id.button_card17,
        R.id.button_card18,
        R.id.button_card19,
        R.id.button_card20,
        R.id.button_card21,
        R.id.button_card22,
        R.id.button_card23,
        R.id.button_card24,
        R.id.button_card25,
    };

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_spymaster);

        Button btnExit = (Button) findViewById(R.id.reg_exit8);
        btnExit.setOnClickListener(this);

        Button btnSendClue = (Button) findViewById(R.id.button_sendclue);
        btnSendClue.setOnClickListener(this);


        text_edit = (EditText)findViewById(R.id.text_spy_guess);

        Intent intent = getIntent();
        lobbyID = intent.getStringExtra("id");
        username = intent.getStringExtra("username");
        team = intent.getStringExtra("team");

        red_score = (TextView) findViewById(R.id.text_red);
        blue_score = (TextView) findViewById(R.id.text_blue);

        header = (Toolbar) findViewById(R.id.title_header);
        
        if (team == ("BLUE"))
        {
            header.setBackgroundTintList(getResources().getColorStateList(R.color.blue));
        }

        seekNumGuesses = (SeekBar) findViewById(R.id.seek_numguesses);
        textNumGuesses = (TextView) findViewById(R.id.text_numguesses);
        textNumGuesses.setText(Integer.toString(seekNumGuesses.getProgress()));

        String w = "ws://10.90.75.56:8080/websocket/games/update/" + username;

        System.out.println("START OF SPYMASTER");

        try {
            dd = new WebSocketClient(new URI(w)) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {

                }

                @Override
                public void onMessage(String s) {
                    if (s.equals("update")) {
                        showScores();
                    } else if (s.equals("blue won")) {
                        dd.close();
                        startActivity(new Intent(SpymasterGameActivity.this, winScreen.class).putExtra("username", username).putExtra("message", "Blue Won"));
                    } else if (s.equals("red won")) {
                        dd.close();
                        startActivity(new Intent(SpymasterGameActivity.this, winScreen.class).putExtra("username", username).putExtra("message", "Red Won"));
                    }
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    System.out.println("There was an issue and it closed");
                    System.out.println("The issue was " + s + " " + i + " " + b);
                }

                @Override
                public void onError(Exception e) {
                    System.out.println(e.toString());
                }
            };
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        dd.connect();


        //Cards

        for (int i=0; i<25; i++)
        {
            cards[i] = (Button)findViewById(CARD_IDS[i]);
            cards[i].setOnClickListener(this);
        }
        showCards();
        showColors();
    }

    public void showScores()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = URL_JSON_SCORE_GET + lobbyID + URL_JSON_SCORE_GET_SECOND;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            red_score_object = object;
                            red_score.setText(red_score_object.getString("redPoints"));
                            blue_score_object = object;
                            blue_score.setText(blue_score_object.getString("bluePoints"));
                            //clue.setText(clue_object.getString("clue"));
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

    /**
     * Makes GET request to add words to each card/button
     * "cards[]" is an array containing each card
     * This method adds each word from the backend onto each card in the array one by one
     */
    public void showCards()
    {
        String url = URL_JSON_CARD_GET + lobbyID + URL_JSON_CARD_GET_SECOND;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
            url, null,
            new Response.Listener<JSONObject>()
            {
                @Override
                public void onResponse(JSONObject response)
                {
                    try
                    {
                        for (int i = 0; i<25; i++) 
                        {
                            Log.d(TAG, response.getString(Integer.toString(i))); //backend in ()
                            //card_name = findViewById(CARD_IDS[i]); //DISCARD
                            cards[i].setText(response.getString(Integer.toString(i))); //display string
                        }
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    /**
     * Makes GET request to add the appropriate color to each card/button
     * "cards[]" is an array containing each card
     * This method adds each color from the backend onto each card in the array one by one
     */
    public void showColors()
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = URL_JSON_GETALLCARDS_SPECTATOR_FIRST + lobbyID + URL_JSON_GETALLCARDS_SPECTATOR_SECOND;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            System.out.println(response);

                            JSONArray object = (JSONArray) (new JSONObject(response).get("cards"));
                            for (int i = 0; i < 25; i++) {
                                JSONObject o = (JSONObject) object.get(i);
                                cards[i].setText(o.get("word").toString());

                                String color = o.get("color").toString();

                                if (color.toLowerCase(Locale.ROOT).equals("red")) {
                                    cards[i].setBackgroundTintList(getResources().getColorStateList(R.color.cardinal));
                                } else if (color.toLowerCase(Locale.ROOT).equals("blue")) {
                                    cards[i].setBackgroundTintList(getResources().getColorStateList(R.color.blue));
                                } else if (color.toLowerCase(Locale.ROOT).equals("grey")) {
                                    cards[i].setBackgroundTintList(getResources().getColorStateList(R.color.gray_2));
                                } else {
                                    cards[i].setBackgroundTintList(getResources().getColorStateList(R.color.black));
                                }
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
     * Makes PUT request to send the spymaster's clue over to the operatives
     * "input" allows the clue to be sent over, and "text_edit" allows the "input" to read what was typed in
     * The JSON object also puts in the user's team and role
     */
    private void sendClue()
    {
        RequestListener addListener = new RequestListener()
        {
            @Override
            public void onSuccess(Object jsonObject)
            {
                JSONObject object = (JSONObject) jsonObject;
                System.out.println(object);
            }
            @Override
            public void onFailure(String error) {
                System.out.println("error");
                System.out.println(error);
            }
        };

        input = text_edit.getText().toString();
        System.out.println(input);
        String url = URL_JSON_CLUE_PUT + lobbyID + URL_JSON_CLUE_PUT_SECOND + input + URL_JSON_CLUE_PUT_THIRD + seekNumGuesses.getProgress();
        JSONObject data = new JSONObject();
        JSONObject d = new JSONObject();
        try {
            d.put("username", username);
            data.put("user", d);
            data.put("role","SPYMASTER");
            data.put("team",team.toUpperCase());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        VolleyListener.makeRequest(this, url, addListener, data, Request.Method.PUT);
    }

    /**
     * Determines what to do when clicking specific buttons
     * @param v receives the id of the button pressed
     */
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.reg_exit8:
                try {
                    leaveLobby();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.button_sendclue:
                sendClue();
                dd.send("update");

            default:
                break;
        }

    }


    /**
     * Removes current player from lobby
     * Uses "username" to let the game know which user to remove from the list of users in game
     * @throws JSONException Exception thrown if Casting error to JSONObject or if key "message" does not exist in JSONObject
     */
    private void leaveLobby() throws JSONException {
        RequestListener leaveListener = new RequestListener() {
            @Override
            public void onSuccess(Object response) throws JSONException {
                System.out.println(response);
                JSONObject object = new JSONObject((String) response);
                if(object.get("message").equals("success")) {
                    //leave and go to hub
                    startActivity(new Intent(SpymasterGameActivity.this, HubActivity.class).putExtra("username", username));
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                System.out.println(errorMessage);
            }
        };

        String url = URL_JSON_REMOVEPLAYER_FIRST + lobbyID + URL_JSON_REMOVEPLAYER_SECOND + username;

        VolleyListener.makeRequest(this, url, leaveListener, Request.Method.DELETE);
    }
}
