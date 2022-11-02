package com.example.codenames;

import static com.example.codenames.utils.Const.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Locale;

public class spectatorViewing extends AppCompatActivity implements View.OnClickListener {

    private Button exit;
    private LinearLayout cardList;
    private String lobbyName;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spectator_viewing);

        exit = (Button) findViewById(R.id.specGame_exit);
        exit.setOnClickListener(this);

        Intent intent = getIntent();
        lobbyName = intent.getStringExtra("lobbyName");
        id = intent.getStringExtra("id");

        // Getting linearlayout
        cardList = (LinearLayout) findViewById(R.id.specGame_cardView);

    }

    private void getCards() {
        String url = URL_JSON_GETALLCARDS_SPECTATOR_FIRST + id + URL_JSON_GETALLcARDS_SPECTATOR_SECOND;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray object = new JSONArray(response);
                            for (int i = 0; i < 5; i++) {
                                LinearLayout row = addRow();

                                for (int j = i*5; j < (i*5) + 5; j++) {
                                    row.addView(getTextView(object.get(j).toString(),
                                            object.get(j).toString()));
                                }

                                cardList.addView(row);
                            }

                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                    }
                });
    }

    private TextView getTextView(String word, String color) {

        TextView t = new TextView(this);
        t.setText(word);
        t.setTextSize(30);
        t.setTextColor(Color.WHITE);

        if (color.toLowerCase(Locale.ROOT).equals("red")) {
            t.setBackgroundColor(Color.RED);
        } else if (color.toLowerCase(Locale.ROOT).equals("blue")) {
            t.setBackgroundColor(Color.BLUE);
        } else if (color.toLowerCase(Locale.ROOT).equals("grey")) {
            t.setBackgroundColor(Color.GRAY);
            t.setTextColor(Color.BLACK);
        } else {
            t.setBackgroundColor(Color.BLACK);
        }

        t.setLayoutParams(new LinearLayout.LayoutParams(0, 0));

        return t;
    }

    private LinearLayout addRow() {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                200));

        return row;
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(spectatorViewing.this, spectatorHub.class));
    }
}