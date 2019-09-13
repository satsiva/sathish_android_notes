/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
//package com.example.system_5.droner;
package com.sathish_android_notes.myapplication.ReadXMLdata;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

public final class  Constants {

    public static String PREF_NAME = "Appname";

    public static final String url = "";
    public static final String imgurl = "";

    public static String networkfailuremsg="No Internet Connection";
    public static String curr_lang="en";
    public static String cliect_email="comments@bahrainbourse.com";

    public static ArrayList<HashMap<String,String>> sliding_img;

    public static ArrayList<HashMap<String,String>> sliding_img_ar;

    public static final int START_AFTER_SECONDS = 3;

    public static JSONArray medias;
    public static ArrayList<HashMap<String, String>> current_ads_array;

    public static ArrayList<HashMap<String, String>> home_array;
    public static ArrayList<HashMap<String, String>> dashboard_array;
    public static ArrayList<HashMap<String, String>> gainers_array;
    public static ArrayList<HashMap<String, String>> market_watch_array;
    public static ArrayList<HashMap<String, String>> timesale_array;
    public static ArrayList<HashMap<String, String>> bhnews_array;
    public static ArrayList<HashMap<String, String>> company_array;
    public static ArrayList<HashMap<String, String>> market_msg_array;
    public static ArrayList<HashMap<String, String>> aboutus_array;
    public static ArrayList<HashMap<String, String>> contactus_array;



    public static LinearLayout bottomdiv;
    public static ImageView bottom_ads_img,bottom_ads_close;

    public static ArrayList<String> slidingimgarr;
    public static int flipping_duration = 1000;

    public static int openaddcount = 0;

    public static final String TAG = "Ads";
    public static final String SEEK_POSITION_KEY = "SEEK_POSITION_KEY";
    public static String VIDEO_URL = "";
    public static String redirectlink = "";
    public static String add_lang = "Both";  //Arabic - English - Both



    public static View mBottomLayout;
    public static View mVideoLayout;
    public static  TextView mStart;

    public static  int mSeekPosition;
    public static  int cachedHeight;
    public static  boolean isFullscreen;

    public static Boolean homepage = true;
    public static Boolean dashpage = true;
    public static Boolean gainloserpage = true;
    public static Boolean marketwpage = true;
    public static Boolean timesalepage = true;
    public static Boolean bhpnewspage = true;
    public static Boolean anouncementpage = true;
    public static Boolean marketmpage = true;
    public static Boolean aboutuspage = true;
    public static Boolean contactuspage = true;

}
