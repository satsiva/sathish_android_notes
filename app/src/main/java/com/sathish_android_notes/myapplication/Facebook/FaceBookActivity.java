package com.sathish_android_notes.myapplication.Facebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.sathish_android_notes.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;


public class FaceBookActivity extends AppCompatActivity {


    CallbackManager callbackManager;
    private static final String EMAIL = "email";
    String facebook_id,f_name,m_name,l_name,full_name,profile_image,email_id="",gender="",statusflag="",user_id_social="",birthday="",user_email="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_book);
       // FacebookSdk.sdkInitialize(getApplicationContext());
        //AppEventsLogger.activateApp(this);


        callbackManager = CallbackManager.Factory.create();//By using CallBackManager.Factory.create we can create a callback manager to handle login responses and we need to set read permissions using setReadPermissions() method to get user details from Facebook.






        LoginButton  loginButton = (LoginButton) findViewById(R.id.login_button);
        //loginButton.setReadPermissions("email","public_profile","gender","birthday");
        loginButton.setReadPermissions("email","public_profile");
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {



                System.out.println("onSuccess");
                String accessToken = loginResult.getAccessToken().getToken();
                Log.i("accessToken", accessToken);
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                Log.i("LoginActivity", response.toString());
                                try {
                                    facebook_id = object.getString("id");
                                    try {
                                        URL profile_pic = new URL("http://graph.facebook.com/" + facebook_id + "/picture?type=large");
                                        Log.i("profile_pic", profile_pic + "");
                                        profile_image= String.valueOf(profile_pic);
                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    }
                                    f_name = object.getString("name");
                                    if(object.has("email"))
                                        email_id = object.getString("email");
                                     user_email =response.getJSONObject().optString("email");
                                    email_id = object.getString("email");
                                    //gender = object.getString("gender");
                                    //birthday = object.getString("birthday");



                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();








                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
