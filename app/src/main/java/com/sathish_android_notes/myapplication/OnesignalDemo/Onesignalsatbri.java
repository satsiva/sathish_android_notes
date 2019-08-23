package com.sathish_android_notes.myapplication.OnesignalDemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//import com.onesignal.OneSignal;
import com.onesignal.OneSignal;
import com.sathish_android_notes.myapplication.R;

public class Onesignalsatbri extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onesignalsatbri);
        OneSignal.startInit(this).init();

    }
}
