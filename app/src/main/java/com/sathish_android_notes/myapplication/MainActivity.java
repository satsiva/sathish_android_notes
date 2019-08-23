package com.sathish_android_notes.myapplication;

import android.annotation.SuppressLint;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    TextView textView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView2=(TextView)findViewById(R.id.textView2);
        textView2.setText(getResources().getString(R.string.app_name));
        textView2.setTextColor(getResources().getColor(R.color.colorAccent));
        textView2.setTextSize(getResources().getDimension(R.dimen.hi));


//or signingReport after covert using this link http://tomeko.net/online_tools/hex_to_base64.php
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String sign= Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.e("MY KEY HASH:", sign);
                //textInstructionsOrLink = (TextView)findViewById(R.id.textstring);
                //textInstructionsOrLink.setText(sign);
                Toast.makeText(getApplicationContext(),sign, Toast.LENGTH_LONG).show();
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("nope","nope");
        } catch (NoSuchAlgorithmException e) {
        }

        //textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
        //textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP,getResources().getDimension(R.dimen.hi));
    }
}
//add project in git up using this link
//https://stackoverflow.com/questions/37093723/how-to-add-an-android-studio-project-to-github


//onesignal
//https://techaxioms.com/how-to-add-onesignal-push-notification-to-android-app/
///http://www.codesenior.com/en/tutorial/Android-Correct-Usage-Onesignal




//error
//1.More than one file was found with OS independent path 'META-INF/LICENSE'
/*
*
* */