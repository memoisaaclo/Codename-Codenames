package com.example.codenames;

import static com.example.codenames.utils.Const.URL_JSON_REGISTRATION;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.codenames.services.RequestListener;
import com.example.codenames.services.VolleyListener;

import org.json.JSONException;
import org.json.JSONObject;

public class registration extends Activity implements OnClickListener {

    private Button register;
    private Button exit;
    private TextView errorText;
    private EditText user;
    private EditText pass;
    private EditText pass2;

    //tags to cancel request
    private String tag_json_obj = "jobj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //initialization
        errorText = (TextView) findViewById(R.id.reg_error_text);

        //button
        register = (Button) findViewById(R.id.register);
        exit = (Button) findViewById(R.id.reg_exit);

        //button click listener
        register.setOnClickListener(regListener);
        exit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.register) {
            //startActivity login
        } else if(v.getId() == R.id.reg_exit) {
            startActivity(new Intent(registration.this, login.class));
        }
    }

    private OnClickListener regListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            user = (EditText) findViewById(R.id.registration_user);
            pass = (EditText) findViewById(R.id.registration_password);
            pass2 = (EditText) findViewById(R.id.registration_password2);

            if (!pass.getText().toString().equals(pass2.getText().toString())) {
                errorText.setText("Passwords do not match");
                return;
            }

            try {
                sendRegistrationInfo(user.getText().toString(), pass.getText().toString());
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }

        }
    };

    private void sendRegistrationInfo(String username, String password) throws JSONException {
        RequestListener registerListener = new RequestListener() {
            @Override
            public void onSuccess(Object jsonObject) {

                JSONObject object = (JSONObject) jsonObject;
                System.out.println(object.toString());
                Intent next = new Intent(getBaseContext(), login.class);

                try {
                    if (object.get("message").equals("success")) {
                        startActivity(next);
                    } else {
                        errorText.setText(object.get("message").toString());
                    }
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

        VolleyListener.makeRequest(this, URL_JSON_REGISTRATION, registerListener, data, Request.Method.POST);
    }


}