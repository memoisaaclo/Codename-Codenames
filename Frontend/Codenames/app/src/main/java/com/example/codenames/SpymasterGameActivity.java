package com.example.codenames;

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
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.codenames.app.AppController;
import com.example.codenames.utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpymasterGameActivity extends AppCompatActivity implements OnClickListener
{
    private String TAG = LobbyActivity.class.getSimpleName();
    private Button btnExit;
    private TextView card_name;

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

        //Cards

        for (int i=0; i<25; i++)
        {
            cards[i] = (Button)findViewById(CARD_IDS[i]);
            cards[i].setOnClickListener(this);
        }
        showCards();
        showColors();

//        for(int i=0; i<25; i++)
//        {
//            cards[i].setBackgroundTintList(getResources().getColorStateList(R.color.gray_2));
//        }
    }

    public void showCards()
    {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
            Const.URL_JSON_CARD_GET, null,
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

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Const.URL_JSON_COLOR_GET, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try
                        {
                            for (int i = 0; i<25; i++)
                            {
                                Log.d(TAG, response.getString("colors")); //backend in ()

                                cards[i].setBackgroundTintList(getResources().getColorStateList(R.color.gray_2));

                                cards[i].setText("SUCCESS");

                                switch (response.getString("colors"))
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
                                    case ("GRAY"):
                                        cards[i].setBackgroundTintList(getResources().getColorStateList(R.color.gray_4));
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

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.reg_exit8:
                startActivity(new Intent(SpymasterGameActivity.this, menu.class));
                break;

//            for (int i=0; i<25;i++)
//            {
//                case CARD_IDS[i]:
//                    break;
//            }

            default:
                break;
        }
    }
}
