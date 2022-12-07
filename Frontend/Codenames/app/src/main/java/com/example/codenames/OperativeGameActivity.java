package com.example.codenames;

/**
 * @author Jimmy Driskell
 */

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import static com.example.codenames.utils.Const.*;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class OperativeGameActivity extends AppCompatActivity implements View.OnClickListener
{
    private String TAG = OperativeGameActivity.class.getSimpleName();
    private Button btnExit;
    private TextView card_name;
    private TextView clue;
    private TextView numGuesses;
    private String lobbyID;
    private String username;
    private JSONObject clue_object;
    private WebSocketClient cc;

    private TextView red_score;
    private TextView blue_score;
    private JSONObject red_score_object;
    private JSONObject blue_score_object;

    private TextView text_turn_color;
    private TextView text_turn_role;
    private JSONObject object_turn_color;
    private JSONObject object_turn_role;

    private String team;
    private androidx.appcompat.widget.Toolbar header;

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_operative);

        Button btnExit = (Button) findViewById(R.id.reg_exit7);
        btnExit.setOnClickListener(this);

        Intent intent = getIntent();
        lobbyID = intent.getStringExtra("id");
        username = intent.getStringExtra("username");
        team = intent.getStringExtra("team");

        clue = (TextView) findViewById(R.id.text_clue);
        numGuesses = (TextView) findViewById(R.id.text_applies);

        red_score = (TextView) findViewById(R.id.text_red2);
        blue_score = (TextView) findViewById(R.id.text_blue2);

        header = (androidx.appcompat.widget.Toolbar) findViewById(R.id.op_title_header);

        if (team == ("BLUE"))
        {
            header.setBackgroundTintList(getResources().getColorStateList(R.color.blue));
        }

        text_turn_color = (TextView) findViewById(R.id.text_turn_color2);
        text_turn_role = (TextView) findViewById(R.id.text_turn_role2);

        //Cards

        for (int i=0; i<25; i++)
        {
            cards[i] = (Button)findViewById(CARD_IDS[i]);
            cards[i].setOnClickListener(this);
        }

        showTurn();
        showCards();
        showColors();

        String w = "ws://10.90.75.56:8080/websocket/games/update/" + username;

        try {
            cc = new WebSocketClient(new URI(w)) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    showCards();
                    showColors();
                    getClue();
                    getNumPlayers();
                }

                @Override
                public void onMessage(String s) {
                    System.out.println("The message is : " + s);
                    if (s.equals("update")) {
                        showCards();
                        showColors();
                        getClue();
                        getNumPlayers();
                    } else if (s.equals("win blue")) {
                        cc.close();
                        startActivity(new Intent(OperativeGameActivity.this, winScreen.class).putExtra("username", username).putExtra("message", "Blue Won")
                                .putExtra("username",username).putExtra("id", lobbyID));
                    } else if (s.equals("win red")) {
                        cc.close();
                        startActivity(new Intent(OperativeGameActivity.this, winScreen.class).putExtra("username", username).putExtra("message", "Red Won")
                                .putExtra("username", username).putExtra("id", lobbyID));
                    }
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
     * Makes GET request to display whose turn it is
     */
    public void showTurn()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = URL_JSON_SCORE_GET + lobbyID + URL_JSON_SCORE_GET_SECOND;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            object_turn_color = object;
                            text_turn_color.setText(object_turn_color.getString("turnColor"));
                            object_turn_role = object;
                            text_turn_role.setText(object_turn_role.getString("turnRole"));
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
     * Makes GET request to display the score for both teams
     */
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
     * Uses cards[], which is an array containing each card
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
                                card_name = findViewById(CARD_IDS[i]); //DISCARD
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
     * Uses cards[], which is an array containing each card
     * This method adds each color from the backend onto each card in the array one by one
     * If the card hasn't been revealed yet, the color will always be gray
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

                            JSONArray object = (JSONArray) (new JSONObject(response).get("cards"));
                            for (int i = 0; i < 25; i++) {
                                JSONObject o = (JSONObject) object.get(i);
                                cards[i].setText(o.get("word").toString());

                                String color = o.get("color").toString();
                                String isRevealed = o.get("revealed").toString();

                                if (isRevealed.toLowerCase(Locale.ROOT).equals("true"))
                                {
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
                                else
                                {
                                    cards[i].setBackgroundTintList(getResources().getColorStateList(R.color.gray_3));
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
     * Makes POST request to add the appropriate color to each card/button
     * @param index selects an individual card from array "cards[]" to reveal
     * "cards[]" is used to call the entire list of cards
     */
    private void revealCard(int index)
    {

        RequestListener listener = new RequestListener() {
            @Override
            public void onSuccess(Object response) throws JSONException {
                JSONObject object = (JSONObject) response;
                System.out.println(object);
            }

            @Override
            public void onFailure(String errorMessage) {
                System.out.println(errorMessage);
            }
        };

        String url = URL_JSON_GUESS_FIRST + lobbyID + URL_JSON_GUESS_SECOND + index;
        JSONObject data = new JSONObject();
        JSONObject d = new JSONObject();
        try {
            d.put("username", username);
            data.put("user", d);
            data.put("role", "OPERATIVE");
            data.put("team", team.toUpperCase());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        VolleyListener.makeRequest(this, url, listener, data, Request.Method.PUT);
        showTurn();

//        String url = URL_JSON_GUESS_FIRST + lobbyID + URL_JSON_GUESS_SECOND + index;
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
//                url, null,
//                new Response.Listener<JSONObject>()
//                {
//                    @Override
//                    public void onResponse(JSONObject response)
//                    {
//                        try
//                        {
//                            Log.d(TAG, response.getString("isrevealed"));
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
//
//                return params;
//            }
//        };
//        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);


        showScores();
    }

    /**
     * Makes GET request to receive clue sent over from the spymaster
     * Uses "clue", which displays the clue in text form
     */
    private void getClue()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = URL_JSON_CLUE_GET + lobbyID + URL_JSON_CLUE_GET_SECOND;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            clue_object = object;
                            clue.setText(clue_object.getString("clue"));
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

//        cc.send("update");
    }

    /**
     * Makes GET request to receive the number of cards applied to the clue
     * Uses "numGuesses", which displays the number of guesses in text form
     */
    private void getNumPlayers()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = URL_JSON_NUMGUESSES_FIRST + lobbyID + URL_JSON_NUMGUESSES_SECOND;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            clue_object = object;
                            numGuesses.setText(clue_object.getString("numGuesses"));
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
     * Determines what to do when clicking specific buttons
     * @param v receives the id of the button pressed
     */
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.reg_exit7:
                try {
                    leaveLobby();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.button_card1: revealCard(0); break;
            case R.id.button_card2: revealCard(1); break;
            case R.id.button_card3: revealCard(2); break;
            case R.id.button_card4: revealCard(3); break;
            case R.id.button_card5: revealCard(4); break;
            case R.id.button_card6: revealCard(5); break;
            case R.id.button_card7: revealCard(6); break;
            case R.id.button_card8: revealCard(7); break;
            case R.id.button_card9: revealCard(8); break;
            case R.id.button_card10: revealCard(9); break;
            case R.id.button_card11: revealCard(10); break;
            case R.id.button_card12: revealCard(11); break;
            case R.id.button_card13: revealCard(12); break;
            case R.id.button_card14: revealCard(13); break;
            case R.id.button_card15: revealCard(14); break;
            case R.id.button_card16: revealCard(15); break;
            case R.id.button_card17: revealCard(16); break;
            case R.id.button_card18: revealCard(17); break;
            case R.id.button_card19: revealCard(18); break;
            case R.id.button_card20: revealCard(19); break;
            case R.id.button_card21: revealCard(20); break;
            case R.id.button_card22: revealCard(21); break;
            case R.id.button_card23: revealCard(22); break;
            case R.id.button_card24: revealCard(23); break;
            case R.id.button_card25: revealCard(24); break;

            default:
                break;
        }
        cc.send("update");

    }

    /**
     * Removes current player from lobby
     * Using "username" to let the game know which user to remove from teh list of users in game
     * @throws JSONException Exception thrown if Casting exception to JSONObject or key "message" does not exist in JSONObject
     */
    private void leaveLobby() throws JSONException {
        RequestListener leaveListener = new RequestListener() {
            @Override
            public void onSuccess(Object response) throws JSONException {

                JSONObject object = new JSONObject((String) response);
                if(object.get("message").equals("success")) {
                    //leave and go to hub
                    startActivity(new Intent(OperativeGameActivity.this, HubActivity.class).putExtra("username", username));
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
