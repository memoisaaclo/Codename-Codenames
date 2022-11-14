package com.example.codenames;

/**
 * @author James Driskell
 */

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
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

public class OperativeGameActivity extends AppCompatActivity implements View.OnClickListener
{
    private String TAG = OperativeGameActivity.class.getSimpleName();
    private Button btnExit;
    private TextView card_name;
    private TextView clue;
    private String lobbyID;
    private String username;
    private JSONObject clue_object;

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

        clue = (TextView) findViewById(R.id.text_clue);

        //Cards

        for (int i=0; i<25; i++)
        {
            cards[i] = (Button)findViewById(CARD_IDS[i]);
            cards[i].setOnClickListener(this);
        }
        showCards();
        showColors();
        getClue();
    }

    /*
    Makes GET request to add words to each card/button
    Uses @param cards[], which is an array containing each card
    This method adds each word from the backend onto each card in the array one by one
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

    /*
    Makes GET request to add the appropriate color to each card/button
    Uses @param cards[], which is an array containing each card
    This method adds each color from the backend onto each card in the array one by one
    If the card hasn't been revealed yet, the color will always be gray
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

                            JSONArray object = new JSONArray(response);
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

    /*
    Makes POST request to add the appropriate color to each card/button
    Uses @param cards[] and "index"
    cards[] is used to call the entire list of cards
    "index" is used to select an individual card from array "cards[]" to reveal
     */
    private void revealCard(int index)
    {
        String url = URL_JSON_REVEAL_GET + lobbyID + URL_JSON_REVEAL_GET_SECOND;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try
                        {
                            Log.d(TAG, response.getString("isrevealed"));
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

    /*
    Makes GET request to receive clue sent over from the spymaster
    Uses @param "clue", which displays the clue in text form
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
    }


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

//            case CARD_IDS[0]:
//                revealCard(0);
//                break;
//            case CARD_IDS[1]:
//                revealCard(1);
//                break;
//            case CARD_IDS[2]:
//                revealCard(2);
//                break;
//            case CARD_IDS[3]:
//                revealCard(3);
//                break;
//            case CARD_IDS[4]:
//                revealCard(4);
//                break;


            default:
                break;
        }
    }

    /*
    Removes current player from lobby
    Using @params username to let the game know which user to remove from the list of users in game
    */
    private void leaveLobby() throws JSONException {
        RequestListener leaveListener = new RequestListener() {
            @Override
            public void onSuccess(Object response) throws JSONException {
                System.out.println(response);
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
