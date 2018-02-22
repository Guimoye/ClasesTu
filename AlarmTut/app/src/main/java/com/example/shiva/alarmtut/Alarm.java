package com.example.shiva.alarmtut;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Alarm extends BroadcastReceiver implements LocationListener {


    LocationManager locationManager;
    double longitudeGPS, latitudeGPS;
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        //Vibrator v = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "TAG");
        // acquire the lock
        wl.acquire();

        LogApp.logfile("ALARMA_CONEXION - Ubicacion: "+ " latitud: " + latitudeGPS + ", longitud: " + longitudeGPS);
       // v.vibrate(1000);
        LogApp.logfile("ALARMA_CONEXION - " + getCurrentTimeStamp() + " -- Internet disponible: " + isNetDisponible());
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 1000, 1, this);

        // release the lock
        wl.release();
    }

    private boolean isNetDisponible() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();
        return (actNetInfo != null && actNetInfo.isConnected());
    }

    public String getCurrentTimeStamp() {
        try {

            Calendar now = Calendar.getInstance();
            DateFormat formatter = SimpleDateFormat.getTimeInstance();
            return formatter.format(now.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        longitudeGPS=location.getLongitude();
        latitudeGPS=location.getLongitude();
        Toast.makeText(context, " GPS", Toast.LENGTH_LONG).show();
        //Vibrator v = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);

        LogApp.logfile("ALARMA_CONEXION - Ubicacion: "
                + " latitud: " + latitudeGPS + ", longitud: " + longitudeGPS);
       // v.vibrate(1000);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
