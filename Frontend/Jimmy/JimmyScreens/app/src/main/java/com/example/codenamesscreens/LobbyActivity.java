package com.example.codenamesscreens;

import static java.lang.Thread.sleep;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.codenamesscreens.AppController;
import com.example.codenamesscreens.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;

public class LobbyActivity extends Activity
{
    private String TAG = LobbyActivity.class.getSimpleName();
//    private Button btnJsonObj, btnJsonArray;
//    private TextView msgResponse;
//    private ProgressDialog pDialog;
    private TextView player_count;

    // These tags will be used to cancel the requests
    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        postJsonObj();
        try
        {
            sleep(100);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        makeJsonObjReq();
    }

    private void postJsonObj()
    {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.POST,
                Const.URL_JSON_PLAYERNUM_POST, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try
                        {
                            Log.d(TAG, response.getString("playerNum"));
                            player_count = findViewById(R.id.text_playercount);
                            player_count.setText(response.getString("playerNum")); //display string
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
            /**
             * Passing some request headers
             * */
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

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }

    /**
     * Making json object request
     * */
    private void makeJsonObjReq()
    {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
                Const.URL_JSON_PLAYERNUM_GET, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try
                        {
                            Log.d(TAG, response.getString("playerNum"));
                            player_count = findViewById(R.id.text_playercount);
                            player_count.setText(response.getString("playerNum")); //display string
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

            /**
             * Passing some request headers
             * */
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
//                params.put("name", "Androidhive");
//                params.put("email", "abc@androidhive.info");
//                params.put("pass", "password123");

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_obj);
    }
}