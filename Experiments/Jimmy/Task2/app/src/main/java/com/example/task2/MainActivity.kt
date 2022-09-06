package com.example.task2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}





<uses-permission android:name="android.permission.SEND_SMS"/>

<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto"
xmlns:tools="http://schemas.android.com/tools"
android:layout_width="match_parent"
android:layout_height="match_parent"
tools:context="com.example.jimmy.message.MainActivity"

<LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        android:orientation="vertical"
        >

<TextView
        android:id="@+id/text"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:text="Complete the fields to send a SMS"
        android:textAppearance="?android:attr/textAppearanceMedium" />

<EditText
        android:id="@+id/phoneNumber"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:ems="10"
        android:hint="ex: 1234567890"
        android:inputType="phone" />

<EditText
        android:id="@+id/smsBody"
        android:layout_width="fill_parent"
        android:layer_height="30dp"
        android:hint="Send the sms by SmsManager" />

<Button
        android:id="@+id/smsManager"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:text="Send the SMS by SmsManager" />

<Button
        android:id="@+id/smsSIntent"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:text="Send the SMS by intent" />

</LinearLayout>
</android.support.constraint.ConstraintLayout>





public class MainActivity extends AppCompatActivity{
    private EditText phoneNumber;
    private EditText smsBody;
    private Button smsManagerBtn;
    private Button smsSendToBtn;
    private Button smsSendToBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phoneNumber = (EditText) findViewByID(R.id.phoneNumber);
        smsBody = (EditText) findViewById(R.id.smsManager);
        smsSendToBtn = (Button) findViewById(R.id.smsSIntent);

        smsManagerBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                sendSmsByManager();
            }
        });

        smsSendToBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                sendSmsByIntent();
            }
        });
    }

    public void sendSmsByManager(){
        try {
            SmsManager smsManager = SmsManager . getDefault ();
            smsManager.sendTextMessage(
                phoneNumber.getText().toString(),
                null,
                smsBody.getText().toString(),
                null,
                null
            );
            Toast.makeText(
                getApplicationContext(), "Your SMS has successfully sent!",
                Toast.LENGTH_LONG
            ).show();
        } catch (Exception ex){
            Toast.makeText(getApplicationContext(),"Your SMS has failed...",
                Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public void sendSmsBySIntent(){
        Uri uri = Uri.parse("smsto:" + phoneNumber.getText().toString());

        Intent smsSIntent = new Intent(Intent.ACTION_SENDTO, uri);
        smsSIntent.putExtra("sms_body", smsBody.getText().toString());
        try{
            startActivity(smsSIntent);
        } catch (Exception ex){
            Toast.makeText(MainActivity.this, "Your SMS has failed...",
                Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
}