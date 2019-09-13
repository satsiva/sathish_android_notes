package com.sathish_android_notes.myapplication.FirebaseTracking;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sathish_android_notes.myapplication.CurrentLocation.GetCurrentLocation;
import com.sathish_android_notes.myapplication.R;
import android.app.Activity;
        import android.support.v4.app.ActivityCompat;
        import android.os.Bundle;
        import android.support.v4.content.ContextCompat;
        import android.content.Intent;
        import android.location.LocationManager;
        import android.Manifest;
        import android.content.pm.PackageManager;
        import android.widget.Toast;

public class FirebaseTrack extends Activity {

    private static final int PERMISSIONS_REQUEST = 100;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check whether GPS tracking is enabled//

        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            finish();
        }

        //Check whether this app has access to the location permission//


        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

//If the location permission has been granted, then start the TrackerService//

        if (permission == PackageManager.PERMISSION_GRANTED) {
            startTrackerService();
        } else {

//If the app doesn’t currently have access to the user’s location, then request access//

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {

//If the permission has been granted...//

        if (requestCode == PERMISSIONS_REQUEST && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            //...then start the GPS tracking service//

            startTrackerService();
        } else {

//If the user denies the permission request, then display a toast with some more information//

            Toast.makeText(this, "Please enable location services to allow GPS tracking", Toast.LENGTH_SHORT).show();
        }








    }

//Start the TrackerService//

    private void startTrackerService() {
        startService(new Intent(this, TrackingService.class));

//Notify the user that tracking has been enabled//

        Toast.makeText(this, "GPS tracking enabled", Toast.LENGTH_SHORT).show();

//Close MainActivity//

        finish();
    }


}






//https://www.androidauthority.com/create-a-gps-tracking-application-with-firebase-realtime-databse-844343/
//https://github.com/JessicaThornsby/GPS-tracker/
//before run app on the location