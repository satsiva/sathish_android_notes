package com.sathish_android_notes.myapplication.ReadXMLdata;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by system-5 on 4/8/2016.
 */
public class LibraryHelper extends AppCompatActivity {

    public static  boolean isNetworkAvailable(Context context) {
        NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (info == null)
        {
            return false;
        }
        else
        {
            if(info.isConnected())
            {
                return true;
            }
            else
            {
                return true;
            }

        }
    }

   public static void alertmessage(String title, String mesage, Context context) {
       android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
       builder.setTitle(title);
       builder.setMessage(mesage);
       builder.setPositiveButton("OK", null);
       android.app.AlertDialog dialog = builder.show();
       //Create custom message
       TextView messageText = (TextView)dialog.findViewById(android.R.id.message);
       messageText.setGravity(Gravity.CENTER);
   }

   public static void confirmAlert(String title, String mesage, Context context) {

   }
    public static String ChangeDateFormat(String datestr,String fromformat,String toformat)
    {
        String time1="";
        //Date currDate = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat(fromformat, Locale.US);

        Date date = null;
        try {
            date = dateFormatter.parse(datestr);
            SimpleDateFormat timeFormatter = new SimpleDateFormat(toformat);
            time1 = timeFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time1;
    }

    public static String ChangeDecimalFormat(String decimal_val,int digit)
    {
        String time1="";
        DecimalFormat df;
        switch (digit){
            case 1:
                df = new DecimalFormat("#,##0.0");
                break;
            case 2:
                df = new DecimalFormat("#,##0.00");
                break;
            case 3:
                df = new DecimalFormat("#,##0.000");
                break;
            case 4:
                df = new DecimalFormat("#,##0.0000");
                break;
            default:
                df = new DecimalFormat("#,##0.#0");
                break;
        }
        double ddd= Double.parseDouble(decimal_val);
        //String chang =df.format(ddd);
        return df.format(ddd);
    }
    public static String ChangeNumberSeperateFormat(String decimal_val,int digit)
    {
        String time1="";
        DecimalFormat df;
        switch (digit){
            case 1:
                df = new DecimalFormat("#,0");
                break;
            case 2:
                df = new DecimalFormat("#,#0");
                break;
            case 3:
                df = new DecimalFormat("#,##0");
                break;
            case 4:
                df = new DecimalFormat("#,###0");
                break;
            default:
                df = new DecimalFormat("#,#0");
                break;
        }
        double ddd= Double.parseDouble(decimal_val);
        //String chang =df.format(ddd);
        return df.format(ddd);
    }

}
