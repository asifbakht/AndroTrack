package com.akstudio.androtrack.services;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

import com.akstudio.androtrack.storage.PreferenceManager;
import com.akstudio.androtrack.track.TrackManager;
import com.akstudio.androtrack.utils.Fields;

/**
 * Created by Asifbakht on 7/24/2016.
 */
public class AndroTrackService extends Service {

    /**
     * we only use Alarm here for GPS tracking as SMS and Call can be tracked using contentObserver
     */

    private ContentResolver contentResolver;
    private Alarm alarm;
    private Uri smsUri = Uri.parse("content://sms/");
    private Uri callUri = android.provider.CallLog.Calls.CONTENT_URI;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate(){
        if (alarm != null) {
            alarm.cancelAlarm(this);
        }
        alarm = new Alarm();
        registerObserver();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }


    private void registerObserver() {
        contentResolver = getContentResolver();
        boolean dataAlarm = false;
        PreferenceManager prefManager = new PreferenceManager(this);
        if (prefManager.getBoolean(Fields.TRACK_SMS)) {
            contentResolver.registerContentObserver(smsUri, true, TrackManager.getInstance(this).smsObserver);
            dataAlarm = true;
        }
        if (prefManager.getBoolean(Fields.TRACK_CALL)) {
            contentResolver.registerContentObserver(callUri, true, TrackManager.getInstance(this).callObserver);
            dataAlarm = true;
        }
        if (prefManager.getBoolean(Fields.TRACK_LOCATION)) {
            alarm.startLocation(this);
            dataAlarm = true;
        }
        if (dataAlarm) {
            alarm.dataUploadAlarm(this);
            alarm.garbageDataCleaningAlarm(this);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        contentResolver.unregisterContentObserver(TrackManager.getInstance(this).smsObserver);
        contentResolver.unregisterContentObserver(TrackManager.getInstance(this).callObserver);
        TrackManager.getInstance(this).resetInstance(this);
        contentResolver = null;
        alarm.cancelAlarm(this);
        alarm = null;
    }
}
