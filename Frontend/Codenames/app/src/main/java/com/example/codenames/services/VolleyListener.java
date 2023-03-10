package com.example.codenames.services;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class VolleyListener {

    private static String baseUrl = "http://10.90.75.56:8080";

    public static void makeRequest(Context context, String path, RequestListener requestListener, JSONObject data, int method){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, baseUrl + path, data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            requestListener.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("onError");
                        System.out.println(error.toString());

                        requestListener.onFailure(error.getMessage());
                        System.out.println("onError pt 2");
                    }
                });
        ApplicationScope.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void makeRequest(Context context, String path, RequestListener requestListener, int method){
        StringRequest stringRequest = new StringRequest(method, baseUrl + path,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            requestListener.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        requestListener.onFailure(error.getMessage());
                    }
                });
        ApplicationScope.getInstance(context).addToRequestQueue(stringRequest);
    }
}
