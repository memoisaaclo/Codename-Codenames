package com.example.codenames;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class SpymasterGameActivity extends AppCompatActivity implements OnClickListener
{
    private Button btnExit;

    private List<Button> cards;
    private static final int[] CARD_IDS = {
        R.id.button_card1,
        R.id.button_card2,
        R.id.button_card3,
        R.id.button_card4,
        R.id.button_card5,
        R.id.button_card6,
        R.id.button_card7,
        R.id.button_card8,
        R.id.button_card9,
        R.id.button_card10,
        R.id.button_card11,
        R.id.button_card12,
        R.id.button_card13,
        R.id.button_card14,
        R.id.button_card15,
        R.id.button_card16,
        R.id.button_card17,
        R.id.button_card18,
        R.id.button_card19,
        R.id.button_card20,
        R.id.button_card21,
        R.id.button_card22,
        R.id.button_card23,
        R.id.button_card24,
        R.id.button_card25,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_spymaster);

        Button btnExit = (Button) findViewById(R.id.reg_exit8);

        btnExit.setOnClickListener(this);

        //Cards

        cards = new ArrayList<Button>(CARD_IDS.length);
        
        for(int id : CARD_IDS)
        {
            Button card = (Button)findViewById(id);
            card.setOnClickListener(this);
            cards.add(card);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.reg_exit8:
                startActivity(new Intent(SpymasterGameActivity.this, menu.class));
                break;
            default:
                break;
        }
    }
}
