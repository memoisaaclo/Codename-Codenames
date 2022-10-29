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

    private Button cards[];

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
        setContentView(R.layout.activity_game_spymaster);

        Button btnExit = (Button) findViewById(R.id.reg_exit8);
        btnExit.setOnClickListener(this);

        //Cards

        Button cards[] = new Button[25];

        cards[0] = (Button)findViewById(R.id.button_card1);
        cards[1] = (Button)findViewById(R.id.button_card2);
        cards[2] = (Button)findViewById(R.id.button_card3);
        cards[3] = (Button)findViewById(R.id.button_card4);
        cards[4] = (Button)findViewById(R.id.button_card5);
        cards[5] = (Button)findViewById(R.id.button_card6);
        cards[6] = (Button)findViewById(R.id.button_card7);
        cards[7] = (Button)findViewById(R.id.button_card8);
        cards[8] = (Button)findViewById(R.id.button_card9);
        cards[9] = (Button)findViewById(R.id.button_card10);
        cards[10] = (Button)findViewById(R.id.button_card11);
        cards[11] = (Button)findViewById(R.id.button_card12);
        cards[12] = (Button)findViewById(R.id.button_card13);
        cards[13] = (Button)findViewById(R.id.button_card14);
        cards[14] = (Button)findViewById(R.id.button_card15);
        cards[15] = (Button)findViewById(R.id.button_card16);
        cards[16] = (Button)findViewById(R.id.button_card17);
        cards[17] = (Button)findViewById(R.id.button_card18);
        cards[18] = (Button)findViewById(R.id.button_card19);
        cards[19] = (Button)findViewById(R.id.button_card20);
        cards[20] = (Button)findViewById(R.id.button_card21);
        cards[21] = (Button)findViewById(R.id.button_card22);
        cards[22] = (Button)findViewById(R.id.button_card23);
        cards[23] = (Button)findViewById(R.id.button_card24);
        cards[24] = (Button)findViewById(R.id.button_card25);

        for (int i=0; i<25; i++)
        {
            cards[i] = (Button)findViewById(CARD_IDS[i]);
            cards[i].setOnClickListener(this);
        }
        showCards();
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
                            //card_name = findViewById(CARD_IDS[i]);
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

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.reg_exit8:
                startActivity(new Intent(SpymasterGameActivity.this, menu.class));
                break;
//            case R.id.button_card1:
//                break;
            default:
                break;
        }
    }
}
