package com.example.codenames.services;

import org.json.JSONException;

public interface RequestListener {
    public void onSuccess(Object response) throws JSONException;
    public void onFailure(String errorMessage);
}
