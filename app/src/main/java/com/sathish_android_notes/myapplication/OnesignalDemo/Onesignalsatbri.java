package com.sathish_android_notes.myapplication.OnesignalDemo;

import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//import com.onesignal.OneSignal;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;
import com.sathish_android_notes.myapplication.Facebook.FaceBookActivity;
import com.sathish_android_notes.myapplication.MainActivity;
import com.sathish_android_notes.myapplication.R;

import org.json.JSONException;

public class Onesignalsatbri extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onesignalsatbri);
        MultiDex.install(getApplicationContext());

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .setNotificationOpenedHandler(new OneSignal.NotificationOpenedHandler() {
                    @Override
                    public void notificationOpened(OSNotificationOpenResult result) {
                        String launchURL = null;
                        try {
                            if (result.notification.payload.additionalData == null ||
                                    result.notification.payload.additionalData.get("CATEGORYID") == null)
                                return;


                           // Constant.CATEGORYID = Integer.valueOf(result.notification.payload.additionalData.get("CATEGORYID").toString());
                           // Constant.PROJECTID = result.notification.payload.additionalData.get("PROJECTID").toString();


                            int hi = Integer.valueOf(result.notification.payload.additionalData.get("CATEGORYID").toString());
                            String hi2 = result.notification.payload.additionalData.get("PROJECTID").toString();

                            Intent intent = new Intent(getApplicationContext(), FaceBookActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                            //Log.e("item", ""+Constant.CATEGORY_ID);
                            startActivity(intent);

                        } catch (JSONException e) {
                            //Intent intent = new Intent(getApplicationContext(), Splash_Activity.class);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                }).init();

    }
}
