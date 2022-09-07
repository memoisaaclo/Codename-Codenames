package com.example.jimmytask1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
/*
<?xml version-"1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto"
xmls:tools="http://schemas.android.com/tools"
android:layout_width="match_parent"
android:layout_height="match_parent"
tools:context="com.example.jimmy.buttons.MainActivity">

<LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent">
        <Button
            android:id="@+id/but1"
            android:layout_width="100dp"
            android:layout_height="200dp"
            android:text="button1"
            />
            <Button
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:id="@+id/but2"
                android:text="button2"
                />
</LinearLayout>

</android.support.constraint.ConstraintLayout>
*/



/* DONE
package com.example.jimmy.buttons;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    Button b1, b2;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1=(Button)findViewById(R.id.but1);
        b2=(Button)findViewById(R.id.but2);
        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, home.class);
                startActivity(i);
            }
        });
        b2.setOnClickListener(new View.On.ClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(getApplicationContext(),"hello button2",Toast.LENGTH_LONG).show();
            }
        });
    }
}
*/




/*
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto"
xmls:tools="http://schemas.android.com/tools"
android:layout_width="match_parent"
android:layout_height="match_parent"
tools:content="com.example.jimmy.buttons.home"
<TextView
android:layout_width="match_parent"
android:layout_height="match_parent"
android:text="hello world"
android:textSize="100dp"
/>
</android.support.constraint.ConstraintLayout>

*/