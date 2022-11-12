package com.example.codenames;

import static com.example.codenames.utils.Const.URL_JSON_LOGIN;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.example.codenames.services.RequestListener;
import com.example.codenames.services.VolleyListener;

import org.json.JSONException;
import org.json.JSONObject;


public class login extends AppCompatActivity implements View.OnClickListener {

    private Button login; // Button to call method to make JSON request to login
    private Button register; // Button to send user to registration screen
    private Button exit; // Button to exit and send user back to menu
    private EditText user; // EditText to get username
    private EditText pass; // EditText to get password


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
        exit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {


        if(v.getId() == R.id.login_login) {
            //startActivity(new Intent(login.this, menu.class));
        } else if (v.getId() == R.id.login_register) {
            startActivity((new Intent(login.this, registration.class)));
        } else if (v.getId() == R.id.login_exit) {
            startActivity(new Intent(login.this, menu.class));
        }

    }

    private View.OnClickListener loginListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            user = (EditText) findViewById(R.id.login_user);
            pass = (EditText) findViewById(R.id.login_password);
            try {
                sendLoginInfo(user.getText().toString(), pass.getText().toString());
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
        }
    };

    /*
    Makes a POST request to login player. If there is no error player will be sent to menu screen. Otherwise error
    message will be displayed. Data sent in body of request includes the username and password.
     */
    private void sendLoginInfo(String username, String password) throws JSONException {
        RequestListener loginListener = new RequestListener() {
            @Override
            public void onSuccess(Object jsonObject) {

                JSONObject object = (JSONObject) jsonObject;

                Intent next = new Intent(login.this, menu.class).putExtra("username", username);

                try {
                    if(object.get("message").equals("success")) {
                        startActivity(next);
                    } else {
                        ((TextView) findViewById(R.id.login_message)).setText("Invalid Credentials");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(String error) {
                System.out.println("error");
                System.out.println(error);
            }
        };

        JSONObject data = new JSONObject();
        data.put("username", username);
        data.put("password", password);

        VolleyListener.makeRequest(this, URL_JSON_LOGIN, loginListener, data, Request.Method.POST);

    }
}