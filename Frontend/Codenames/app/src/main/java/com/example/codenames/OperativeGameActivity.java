package com.example.codenames;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

public class OperativeGameActivity extends AppCompatActivity implements View.OnClickListener
{
    private String TAG = OperativeGameActivity.class.getSimpleName();
    private Button btnExit;
    private TextView card_name;
    private TextView clue;

    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";

    private Button cards[] = new Button[25];
    //cards[] = new Button[25];

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

    public void showColors()
    {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Const.URL_JSON_COLOR_REVEAL, null,
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



    private void revealCard(int index)
    {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Const.URL_JSON_REVEAL_GET, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try
                        {
                            Log.d(TAG, response.getString("isRevealed"));
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


    private void getClue()
    {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Const.URL_JSON_CLUE_GET, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try
                        {
                            Log.d(TAG, response.getString("clue")); //backend in ()
                            clue = findViewById(R.id.text_clue);
                            clue.setText(response.getString("clue")); //display string
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
            case R.id.reg_exit7:
                startActivity(new Intent(OperativeGameActivity.this, menu.class));
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
}
