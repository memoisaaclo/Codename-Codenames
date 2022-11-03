package com.example.codenames;

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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.codenames.app.AppController;
import com.example.codenames.services.RequestListener;
import com.example.codenames.services.VolleyListener;
import com.example.codenames.utils.Const;
import static com.example.codenames.utils.Const.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

        //Cards

        for (int i=0; i<25; i++)
        {
            cards[i] = (Button)findViewById(CARD_IDS[i]);
            cards[i].setOnClickListener(this);
        }
        showCards();
        showColors();
    }

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

    public void showColors()
    {
        //cards[0].setBackgroundTintList(getResources().getColorStateList(R.color.cardinal));
        Strin url = URL_JSON_GETALLCARDS_SPECTATOR_FIRST + lobbyID + URL_JSON_GETALLCARDS_SPECTATOR_SECOND;
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

                                 //checks is revealed
                                switch (response.getString(Integer.toString(i)))
                                {
                                    case ("RED"):
                                        cards[i].setBackgroundTintList(getResources().getColorStateList(R.color.cardinal));
                                        break;
                                    case ("BLUE"):
                                        cards[i].setBackgroundTintList(getResources().getColorStateList(R.color.blue));
                                        break;
                                    case ("BLACK"):
                                        cards[i].setBackgroundTintList(getResources().getColorStateList(R.color.black));
                                        break;
                                    case ("GREY"):
                                        cards[i].setBackgroundTintList(getResources().getColorStateList(R.color.gray_2));
                                        break;
                                }
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

    private void sendClue()
    {
        RequestListener addListener = new RequestListener() {
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
        JSONObject data = new JSONObject();
        try {
            data.put("clue",input);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        VolleyListener.makeRequest(this, URL_JSON_CLUE_PUT, addListener, data, Request.Method.PUT);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.reg_exit8:
                startActivity(new Intent(SpymasterGameActivity.this, menu.class));
                break;

            case R.id.button_sendclue:
                sendClue();

            default:
                break;
        }
    }
}
