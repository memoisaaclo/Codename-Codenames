package com.example.codenames;

/**
 * @author James Driskell
 */

import static com.example.codenames.utils.Const.URL_JSON_WORD_ADD;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.CompoundButtonCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

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

        //Cards

        for (int i=0; i<25; i++)
        {
            cards[i] = (Button)findViewById(CARD_IDS[i]);
            cards[i].setOnClickListener(this);
        }
        showCards();
        showColors();
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


    /*
    Makes PUT request to send the spymaster's clue over to the operatives
    Takes @params input and "text_edit", with "input" allowing the clue to be sent over, and "text_edit" allowing the "input" to read what was typed in
    The JSON object also puts in the user's team and role
    */
    private void sendClue()
    {
        String url = URL_JSON_CLUE_PUT + lobbyID + URL_JSON_CLUE_PUT_SECOND + input + URL_JSON_CLUE_PUT_THIRD + "2";
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
        url = URL_JSON_CLUE_PUT + lobbyID + URL_JSON_CLUE_PUT_SECOND + input + URL_JSON_CLUE_PUT_THIRD + "2";
        JSONObject data = new JSONObject();
        try {
            data.put("role","SPYMASTER");
            data.put("team","RED");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        VolleyListener.makeRequest(this, url, addListener, data, Request.Method.PUT);
    }

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
