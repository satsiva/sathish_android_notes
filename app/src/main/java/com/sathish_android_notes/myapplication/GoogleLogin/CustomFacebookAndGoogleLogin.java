package com.sathish_android_notes.myapplication.GoogleLogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.onesignal.OneSignal;
import com.sathish_android_notes.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;


public class CustomFacebookAndGoogleLogin extends AppCompatActivity implements  GoogleApiClient.OnConnectionFailedListener {


    String jsonStr="",userid_normal="";
    String onesignaluserId="";
    LoginButton loginButton;//for fb
    CallbackManager callbackManager;//for fb
    Button fb,gooid;
    String facebook_id,f_name,m_name,l_name,full_name,profile_image,email_id="",gender="",statusflag="",user_id_social="",birthday="";

    GoogleApiClient mGoogleApiClient;//for gmail
    private static final int RC_SIGN_IN = 9001;//for gmail
    String accid,accname,accemail,personPhotoUrl;
    private static final String TAG = "SignInActivity";

    String social_type="";
    String login_already="";
//    LetterSpacingTextView Title2;
    ImageView backmain;
    Button forgot,glogout,flogout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);


        FacebookSdk.sdkInitialize(getApplicationContext());//for fb
        callbackManager = CallbackManager.Factory.create();//for fb


        glogout=findViewById(R.id.glogout);
        flogout=findViewById(R.id.flogout);

        glogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {

                            }
                        });

            }
        });


        flogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginManager.getInstance().logOut();//for fb logout

            }
        });


        OneSignal.startInit(this).init();
        //for google start
        gooid=(Button) findViewById(R.id.gooid);
        gooid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        //for google end





        fb = (Button) findViewById(R.id.fb);
        loginButton = (LoginButton) findViewById(R.id.login_button);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.performClick();
            }
        });



//        List < String > permissionNeeds = Arrays.asList("user_photos", "email",
//                "user_birthday", "public_profile", "AccessToken");
        loginButton.setReadPermissions("email","public_profile");
        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                public void onSuccess(LoginResult loginResult) {

                    System.out.println("onSuccess");

                    String accessToken = loginResult.getAccessToken()
                            .getToken();
                    Log.i("accessToken", accessToken);

                    GraphRequest request = GraphRequest.newMeRequest(
                            loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                            public void onCompleted(JSONObject object,
                                                    GraphResponse response) {

                                Log.i("LoginActivity",
                                        response.toString());
                                try {
                                    facebook_id = object.getString("id");
                                    try {
                                        URL profile_pic = new URL(
                                                "http://graph.facebook.com/" + facebook_id + "/picture?type=large");
                                        Log.i("profile_pic",
                                                profile_pic + "");

                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    }
                                    f_name = object.getString("name");
                                    if(object.has("email"))
                                        email_id = object.getString("email");
//                                    String user_email =response.getJSONObject().optString("email");
//                                    email_id = object.getString("email");
                                    gender = object.getString("gender");
                                   // birthday = object.getString("birthday");

                                    social_type="facebook";
                                    //callserver();
                                    OneSignal.startInit(CustomFacebookAndGoogleLogin.this).init();
                                    OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
                                        @Override
                                        public void idsAvailable(String userId, String registrationId) {
                                            String text = "OneSignal UserID:\n" + userId + "\n\n";
                                            onesignaluserId=userId;

                                            if (registrationId != null){
                                                text += "Google Registration Id:\n" + registrationId;
                                            }

                                            else
                                                text += "Google Registration Id:\nCould not subscribe for push";

                                        }
                                    });
                                    //OneSignal.enableNotificationsWhenActive(true);
                                   // new SocialRegister().execute();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields",
                            "id,name,email,gender, birthday");
                    request.setParameters(parameters);
                    request.executeAsync();
                }

                    @Override
                    public void onCancel() {
                        System.out.println("onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        System.out.println("onError");
//                        Log.v("LoginActivity", exception.getCause().toString());
                    }
                });

    }



    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    public void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }





    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent data) {
        super.onActivityResult(requestCode, responseCode, data);
        callbackManager.onActivityResult(requestCode, responseCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

//    public void onClick(View v) {
//        if (v == fb) {
//            loginButton.performClick();
//        }
//    }

    public void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess())
        {
            GoogleSignInAccount acct = result.getSignInAccount();
            accid=acct.getId();
            accname=acct.getDisplayName();
            if(acct.getEmail() != null && !acct.getEmail().equals("null"))
            {
                accemail=acct.getEmail();
            }
            else
            {
                accemail="";
            }
            if(acct.getPhotoUrl() != null && !acct.getPhotoUrl().equals("null"))
            {
                Log.e("userurl", acct.getPhotoUrl().toString());
                personPhotoUrl = acct.getPhotoUrl().toString();
            }
            else
            {
                Log.e("userurl","null valu and empty value");
            }

            Log.e("gname", accname);
            Log.e("gemail", accemail);

            social_type="google";

            OneSignal.startInit(CustomFacebookAndGoogleLogin.this).init();
            OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
                @Override
                public void idsAvailable(String userId, String registrationId) {
                    String text = "OneSignal UserID:\n" + userId + "\n\n";
                    onesignaluserId=userId;

                    if (registrationId != null){
                        text += "Google Registration Id:\n" + registrationId;
                    }

                    else
                        text += "Google Registration Id:\nCould not subscribe for push";

                }
            });
            //OneSignal.enableNotificationsWhenActive(true);
           // new SocialRegister().execute();


        } else {
            // Signed out, show unauthenticated UI.
//             updateUI(false);
        }
    }


}
