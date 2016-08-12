package com.akstudio.androtrack.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.akstudio.androtrack.storage.DatabaseHelper;
import com.akstudio.androtrack.utils.Fields;

/**
 * Created by Asifbakht on 7/24/2016.
 */
public class Alarm extends BroadcastReceiver {

    private final int payLoadTime = (int) AlarmManager.INTERVAL_FIFTEEN_MINUTES;// 1000 * 60 * 1; // millisecond seconds minutes // 2 minute
    private final int gpsLoadTime = (int) AlarmManager.INTERVAL_HALF_HOUR;//1000 * 60 * 1; // millisecond seconds minutes // 1 minute
    private final int garbageTime = (int) AlarmManager.INTERVAL_HALF_DAY;//1000 * 60 * 1; // millisecond seconds minutes // 1 minute
    private final int dataAlarm = 6650438;
    private final int gpsAlarm = 6650717;
    private final int garbageAlarm = 6650979;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Fields.DATA_UPLOAD)) {
            Intent dataIntent = new Intent(context, DataPayloadService.class);
            dataIntent.setAction(intent.getAction());
            context.startService(dataIntent);
        } else if (intent.getAction().equals(Fields.GARBAGE_TRASH)) {
            DatabaseHelper.getInstance(context).deleteDirty();
        } else {
            LocationService.requestLocation(context, intent);
        }
    }


    protected void dataUploadAlarm(Context context) {
        AlarmManager gpsalarmAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Alarm.class);
        intent.setAction(Fields.DATA_UPLOAD);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, dataAlarm, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        gpsalarmAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), payLoadTime, pendingIntent); // Millisec * Second * Minute
    }


    protected void startLocation(Context context) {
        AlarmManager gpsalarmAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Alarm.class);
        intent.setAction(Fields.LOCATION_TRACK);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, gpsAlarm, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        gpsalarmAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), gpsLoadTime, pendingIntent); // Millisec * Second * Minute
    }


    protected void garbageDataCleaningAlarm(Context context) {
        AlarmManager gpsalarmAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Alarm.class);
        intent.setAction(Fields.GARBAGE_TRASH);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, garbageAlarm, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        gpsalarmAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), garbageTime, pendingIntent); // Millisec * Second * Minute
    }


    protected void cancelAlarm(Context context) {
        Intent intent = new Intent(context, Alarm.class);
        intent.setAction(Fields.DATA_UPLOAD);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, dataAlarm, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        intent = new Intent(context, Alarm.class);
        intent.setAction(Fields.LOCATION_TRACK);
        pendingIntent = PendingIntent.getBroadcast(context, gpsAlarm, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent);

        intent = new Intent(context, Alarm.class);
        intent.setAction(Fields.GARBAGE_TRASH);
        pendingIntent = PendingIntent.getBroadcast(context, garbageAlarm, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent);

    }
}