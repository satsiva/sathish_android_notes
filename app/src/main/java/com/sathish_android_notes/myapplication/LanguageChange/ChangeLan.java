package com.sathish_android_notes.myapplication.LanguageChange;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.sathish_android_notes.myapplication.R;
import com.sathish_android_notes.myapplication.ReadXMLdata.Constants;

import java.util.Locale;

public class ChangeLan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_lan);


        ImageView lang_btn = (ImageView) findViewById(R.id.lang_btn);
        lang_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(Constants.curr_lang.equals("en"))
                {
                    SharedPreferences preferences = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("clang", "ar");
                    editor.commit();

                    String languageToLoad = "ar";
                    Locale locale2 = new Locale(languageToLoad);
                    Locale.setDefault(locale2);
                    Constants.curr_lang="ar";
                    Configuration config2 = new Configuration();
                    config2.locale = locale2;
                    getBaseContext().getResources().updateConfiguration(config2, getBaseContext().getResources().getDisplayMetrics());
                    finish();
                    startActivity(getIntent());
                }
                else
                {
                    SharedPreferences preferences = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("clang", "en");
                    editor.commit();


                    String languageToLoad = "en";
                    Locale locale2 = new Locale(languageToLoad);
                    Locale.setDefault(locale2);
                    Constants.curr_lang="en";
                    Configuration config2 = new Configuration();
                    config2.locale = locale2;
                    getBaseContext().getResources().updateConfiguration(config2,
                            getBaseContext().getResources().getDisplayMetrics());
                    finish();
                    startActivity(getIntent());
                    return;
                }
            }
        });


    }
}
