package com.example.codenames;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class OperativeGameActivity extends AppCompatActivity implements OnClickListener
{
    private Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_operative);

        Button btnExit = (Button) findViewById(R.id.reg_exit7);

        btnExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.reg_exit7:
                startActivity(new Intent(OperativeGameActivity.this, menu.class));
                break;
            default:
                break;
        }
    }
}
