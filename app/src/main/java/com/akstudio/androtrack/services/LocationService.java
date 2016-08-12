package com.akstudio.androtrack.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.os.PowerManager;

import com.akstudio.androtrack.track.LocationTrack;

/**
 * Created by Asifbakht on 7/24/2016.
 */
public class LocationService extends Service {

    public static final String WAKE_LOCK_NAME = "com.akstudio.services.POWER_MANAGER";
    private static volatile PowerManager.WakeLock wakeLockStatic = null;
    private LocationManager locationManager = null;


    public static void requestLocation(Context locationContext, Intent locationIntent) {
        getLock(locationContext).acquire();
        locationIntent.setClass(locationContext, LocationService.class);
        locationIntent.putExtra(LocationTrack.LOCATION_EXTRA,new Intent(locationContext, LocationListener.class));
        locationContext.startService(locationIntent);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        PowerManager.WakeLock lock = getLock(this.getApplicationContext());
        if (!lock.isHeld() || (flags & START_FLAG_REDELIVERY) != 0) {
            lock.acquire();
        }
        new LocationTrack(lock, this, locationManager, intent).start();
        return (START_REDELIVER_INTENT);
    }


    synchronized private static PowerManager.WakeLock getLock(Context context) {
        if (wakeLockStatic == null) {
            PowerManager powerManager = (PowerManager) context.getApplicationContext().getSystemService(Context.POWER_SERVICE);
            wakeLockStatic = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, WAKE_LOCK_NAME);
            wakeLockStatic.setReferenceCounted(true);
        }
        return (wakeLockStatic);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
