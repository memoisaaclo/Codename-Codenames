package com.example.codenames.services;

public interface RequestListener {
    public void onSuccess(Object response);
    public void onFailure(String errorMessage);
}
