package com.example.codenames;

/**
 * @author Jimmy Driskell
 */

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import static com.example.codenames.utils.Const.*;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.codenames.app.AppController;
import com.example.codenames.utils.Const;

import org.json.JSONArray;
import org.json.JSONObject;

public class AdminUsersActivity extends AppCompatActivity implements View.OnClickListener
{
    private String TAG = AdminUsersActivity.class.getSimpleName();
    private EditText text_edit;
    private String input;
    private Button delete_user;
    private Button btnExit;
    private TextView user_list;
    private String username;
    private LinearLayout user_scroll;

    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        text_edit = (EditText)findViewById(R.id.text_wordsearch);
        delete_user = (Button) findViewById(R.id.button_delete);
        btnExit = (Button) findViewById(R.id.reg_exit4);

        user_scroll = (LinearLayout) findViewById(R.id.user_list);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        delete_user.setOnClickListener(this);
        btnExit.setOnClickListener(this);

        showUsers();
    }

    /**
     * Makes DELETE request to remove user from the database
     * Uses "queue" and "request"
     * "queue" queues the request
     * "request" contains the username of the user that is about to be deleted
     */
    private void deleteUser()
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.DELETE, URL_JSON_WORD_DELETE + text_edit.getText().toString(),
                new Response.Listener<String> () {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                    }
                });
        queue.add(request);
    }

    /**
     * Makes GET request to show every username in a list
     * Uses "user_list" to print the usernames onto a text view for the admins to see visibly
     */
    private void showUsers()
    {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.GET,
                Const.URL_JSON_ALL_USERS, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        Log.d(TAG, response.toString()); //backend in ()
                        user_list = findViewById(R.id.textView);
                        user_list.setText(response.toString()); //display string
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(jsonArrReq, tag_json_arry);
    }

    /**
     * Determines what to do when clicking specific buttons
     * @param v receives the id of the button pressed
     */
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.reg_exit4:
                startActivity(new Intent(AdminUsersActivity.this, menu.class).putExtra("username", username));
                break;

            case R.id.button_delete:
                deleteUser();
                break;

            default:
                break;
        }
    }
}
