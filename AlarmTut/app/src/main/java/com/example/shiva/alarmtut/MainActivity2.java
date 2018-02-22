package com.example.shiva.alarmtut;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity2 extends AppCompatActivity {
    Button btnSet, btn_generarbck;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSet          = (Button) findViewById(R.id.btnSetAlarm);
        btn_generarbck  = (Button) findViewById(R.id.btn_generarbck);
        activity        = this;

        btn_generarbck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   Functions.backupDB(activity);
                final Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"javier.guimoye@targetmaps.pe"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "AlarmMager" + " : Log report");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "The attached files will help us to improve the quality of us service.");

                //has to be an ArrayList
                ArrayList<Uri> uris = new ArrayList<>();
                //convert from paths to Android friendly Parcelable Uri's
                String filename = "AlarmaManager.slo";
                File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filename);
                Uri path = Uri.fromFile(filelocation);
                uris.add(path);

                emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
                activity.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }
        });

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.System.canWrite(activity)) {
                        requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                android.Manifest.permission.READ_EXTERNAL_STORAGE}, 2909);
                    }
                }
                LogApp.logfile("ALARMA_CONEXION - presiono boton para activar alarma");
                btnSet.setText("alarma activada");
             try {

                 long delay = TimeUnit.MINUTES.toMillis(3L); //5 minutos
                 long time = System.currentTimeMillis() + delay; // tiempo actual + los 5min
                    Intent intent       =   new Intent(MainActivity2.this, Alarm.class);
                    PendingIntent p1    =   PendingIntent.getBroadcast(getApplicationContext(),0, intent,0);
                    AlarmManager am     =   (AlarmManager)getSystemService(ALARM_SERVICE);

                 //verificar las versiones para activar alarma
                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                     // Wakes up the device in Doze Mode
                     am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, p1);
                 } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                     // Wakes up the device in Idle Mode
                     am.setExact(AlarmManager.RTC_WAKEUP, time, p1);
                 } else {
                     // Old APIs
                     am.set(AlarmManager.RTC_WAKEUP, time, p1); }

                }catch (NumberFormatException e){ }

            }
        });
    }


}
