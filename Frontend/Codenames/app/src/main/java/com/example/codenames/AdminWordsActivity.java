package com.example.codenames;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.codenames.app.AppController;
import com.example.codenames.services.RequestListener;
import com.example.codenames.services.VolleyListener;
import com.example.codenames.utils.Const;

import static com.example.codenames.utils.Const.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdminWordsActivity extends AppCompatActivity implements View.OnClickListener
{
    private String TAG = AdminWordsActivity.class.getSimpleName();
    private EditText text_edit;
    private String input;
    private Button add_word;
    private Button delete_word;
    private Button btnExit;
    private TextView word_list;
    private String username;

    private String tag_json_obj = "jobj_req", tag_json_arry = "jarray_req";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);

        text_edit = (EditText)findViewById(R.id.text_wordsearch);
        add_word = (Button) findViewById(R.id.button_add);
        delete_word = (Button) findViewById(R.id.button_delete);
        btnExit = (Button) findViewById(R.id.reg_exit5);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        add_word.setOnClickListener(this);
        delete_word.setOnClickListener(this);
        btnExit.setOnClickListener(this);

        showWords();
    }

    private void addWord()
    {
        RequestListener addListener = new RequestListener() {
            @Override
            public void onSuccess(Object jsonObject)
            {
                JSONObject object = (JSONObject) jsonObject;
                System.out.println(object);
            }
            @Override
            public void onFailure(String error) {
                System.out.println("error");
                System.out.println(error);
            }
        };

        input = text_edit.getText().toString();
        System.out.println(input);
        JSONObject data = new JSONObject();
        try {
            data.put("word",input);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        VolleyListener.makeRequest(this, URL_JSON_WORD_ADD, addListener, data, Request.Method.PUT);
    }

    private void deleteWord()
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.DELETE, "http://10.90.75.56:8080/admin/cards/remove/" + text_edit.getText().toString(),
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

    private void showWords()
    {
        JsonArrayRequest jsonArrReq = new JsonArrayRequest(Request.Method.GET,
                Const.URL_JSON_ALL_CARDS, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        Log.d(TAG, response.toString()); //backend in ()
                        word_list = findViewById(R.id.textView);
                        word_list.setText(response.toString()); //display string
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


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.reg_exit5:
                startActivity(new Intent(AdminWordsActivity.this, menu.class).putExtra("username", username));
                break;

            case R.id.button_add:
                addWord();
                break;

            case R.id.button_delete:
                deleteWord();
                break;

            default:
                break;
        }
    }
}
