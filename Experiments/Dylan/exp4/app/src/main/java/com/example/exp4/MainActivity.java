package com.example.exp4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements FragmentFirst.FragmentFirstListener, FragmentSecond.FragmentSecondListener {

    private FragmentFirst fragFirst;
    private FragmentSecond fragSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragFirst = new FragmentFirst();
        fragSecond = new FragmentSecond();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_first, fragFirst)
                .replace(R.id.container_second, fragSecond)
                .commit();
    }

    @Override
    public void onInputFirstSent(CharSequence input) {
        fragSecond.updateText(input);
    }

    @Override
    public void onInputSecondSent(CharSequence input) {
        fragFirst.updateText(input);
    }
}