package com.example.codenames;

/**
 * @author Dylan Booth
 */

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

    private Button register; // Button to forward user to the registration screen.
    private Button exit; // Button to return user back to the menu screen
    private TextView errorText; // TextView to display the error message if there is an error/problem with registration
    private EditText user; // EditText to hold/get the username to be registered
    private EditText pass; // EditText to hold/get the password to be registered
    private EditText pass2; // EditText to hold/get the password to compare and be registered
    private String username; // String to hold value of the registered username. Used when going back to menu screen

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

        Intent intent = getIntent();

        username = intent.getStringExtra("username");

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.register) {
            //startActivity login
        } else if(v.getId() == R.id.reg_exit) {
            startActivity(new Intent(registration.this, login.class).putExtra("username", username));
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

    /**
     * Makes a POST request. Sends data to register player in database, then log them in.
     * @param username String value of new users username
     * @param password String value of new users password
     * @throws JSONException
     */
    private void sendRegistrationInfo(String username, String password) throws JSONException {
        RequestListener registerListener = new RequestListener() {
            @Override
            public void onSuccess(Object jsonObject) {

                JSONObject object = (JSONObject) jsonObject;
                System.out.println(object.toString());
                Intent next = new Intent(getBaseContext(), menu.class).putExtra("username", username);

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