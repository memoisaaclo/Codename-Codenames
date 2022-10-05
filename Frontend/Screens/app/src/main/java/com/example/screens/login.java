package com.example.screens;

import static com.example.screens.utils.Const.URL_JSON_LOGIN;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.example.screens.services.RequestListener;
import com.example.screens.services.VolleyListener;

import org.json.JSONException;
import org.json.JSONObject;


public class login extends AppCompatActivity implements View.OnClickListener {

    private Button login;
    private Button register;
    private Button exit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //buttons
        login = (Button) findViewById(R.id.login_login);
        register = (Button) findViewById(R.id.login_register);
        exit = (Button) findViewById(R.id.login_exit);

        //button click listeners
        login.setOnClickListener(loginListener);
        register.setOnClickListener(this);
//        exit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {


        if(v.getId() == R.id.login_login) {
            startActivity(new Intent(login.this, menu.class));
        } else if (v.getId() == R.id.login_register) {
            startActivity((new Intent(login.this, registration.class)));
        } else if (v.getId() == R.id.login_exit) {
            startActivity(new Intent(login.this, menu.class));
        }

    }

    private View.OnClickListener loginListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText user = (EditText) findViewById(R.id.login_user);
            EditText pass = (EditText) findViewById(R.id.login_password);
            try {
                sendLoginInfo(user.getText().toString(), pass.getText().toString());
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
        }
    };

    private void sendLoginInfo(String username, String password) throws JSONException {
        RequestListener loginListener = new RequestListener() {
            @Override
            public void onSuccess(Object jsonObject) {
                JSONObject object = (JSONObject) jsonObject;
                Intent next = new Intent(getBaseContext(), menu.class);
                try {
                    next.putExtra("message", object.get("message").toString());
                    startActivity(next);
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
            }
            @Override
            public void onFailure(String error) {
                System.out.println(error);
            }
        };

        JSONObject data = new JSONObject();
        data.put("username", username);
        data.put("password", password);

        System.out.println(data.toString() + "\n " + URL_JSON_LOGIN);

        VolleyListener.makeRequest(getApplicationContext(), URL_JSON_LOGIN, loginListener, data, Request.Method.GET);
    }
}