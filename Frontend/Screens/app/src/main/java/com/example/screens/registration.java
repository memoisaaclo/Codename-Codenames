package com.example.screens;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.example.screens.app.AppController;
import com.example.screens.utils.Const;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class registration extends Activity implements OnClickListener {

    private Button register;
    private String TAG;
    private ProgressDialog pDialog;
    private String msgResponse;

    //tags to cancel request
    private String tag_json_obj = "jobj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //initialization
        msgResponse = "";

        //button
        register = (Button) findViewById(R.id.register);

        //button click listener
        register.setOnClickListener(this);

        //progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing()) {
            pDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing()) {
            pDialog.hide();
        }
    }

    /**
     * Making json object request
     */

    private void makeJsonObjReq() {
        showProgressDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Const.URL_JSON_OBJECT, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        String msgResponse = "";
                        msgResponse = response.toString();
                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                //JSON Request Parameters for registrations
                params.put("username", ((TextView) findViewById(R.id.reg_user)).toString());
                params.put("password", ((TextView) findViewById(R.id.reg_pass)).toString());

                return params;
            }

        };

        if ((TextView) findViewById(R.id.reg_pass) == (TextView) findViewById(R.id.reg_pass2)) {
            // adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        }   else {
            //cancelling request
            AppController.getInstance().getRequestQueue().cancelAll(tag_json_obj);

            //output error message to say "passowrds dont match"
        }

    }

    @Override
    public void onClick(View view) {

        makeJsonObjReq();

        if (msgResponse == "success") {
            startActivity(new Intent
                    (registration.this, menu.class));
        } else {
            //output error message to say "username already exists"
        }
    }
}