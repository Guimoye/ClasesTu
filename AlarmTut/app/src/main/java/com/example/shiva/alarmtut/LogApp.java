package com.example.shiva.alarmtut;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;

public class LogApp {
    public static String ARCHIVO_LOG="AlarmaManager.slo";
    static String  alarma = "AlarmaManager";

    public static void log(String texto,String... tagLog) {
        String MytagLog;
        if(tagLog.length>0) MytagLog=tagLog[0];
        else MytagLog = alarma;
        try {
            Log.i(""+MytagLog, ""+texto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void logfile(String texto,Boolean... params) {
        Log.e(""+alarma, ""+texto);
        Boolean append = params.length > 0 ? params[0] : true;//appned :true, overwrite: false
        try{
            File root = Environment.getExternalStorageDirectory().getAbsoluteFile();

//			Log.e(""+Constants.TAG_NAVEGO, ""+root.getPath());
//			Log.e(""+Constants.TAG_NAVEGO, ""+texto);
            if (root.canWrite()){
                File logfile = new File(root,ARCHIVO_LOG);
                FileWriter gpxwriter = new FileWriter(logfile,append);
                BufferedWriter out = new BufferedWriter(gpxwriter);
                Calendar c = Calendar.getInstance();
                out.write(DateUtil.fromCalendar(c)+"|"+texto+"\n");
                out.close();
            }
        }catch( Exception e){
            e.printStackTrace();
            LogApp.logfile("LogApp logfile - Error: " + e.getLocalizedMessage());
        }
    }

}
