package com.akstudio.androtrack.track;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;

import com.akstudio.androtrack.services.LocationService;
import com.akstudio.androtrack.services.WakefulThread;

/**
 * Created by Asifbakht on 7/24/2016.
 */
public class LocationTrack extends WakefulThread {


    private static final String LOCATION_ERROR = "com.akstudio.track.LOCATION_ERROR";
    public static final String LOCATION_EXTRA = "com.akstudio.track.LOCATION_LISTENER";
    final String LOCATION_CURRENT = "com.akstudio.track.LOCATION_CURRENT";
    final String LOCATION_PROVIDER_DISABLED = "com.akstudio.track.EXTRA_ERROR_PROV_DISABLED";
    final String LOCATION_ACTION = "com.akstudio.track.LOCATION_BROADCAST";


    static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 50; // 10 meters
    static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;//AlarmManager.INTERVAL_FIFTEEN_MINUTES;//(1000 * 60 * 1); // 1 minute


    Intent locationIntent = null;
    Runnable onTimeout = null;
    Handler handler = new Handler();
    final int DEFAULT_TIMEOUT = 90000; // 1 and half minutes
    int TIMEOUT = DEFAULT_TIMEOUT;

    Context locationContext;
    LocationManager locationManager;
    boolean isGpsEnabled = false, isNetworkEnabled = false;


    public LocationTrack(String name) {
        super(name);
    }


    public LocationTrack(PowerManager.WakeLock lock, Context locationContext, LocationManager locationManager, Intent intentTemplate) {
        super(lock, "LocationPoller");
        this.locationManager = locationManager;
        this.locationIntent = intentTemplate;
        this.locationContext = locationContext;
        this.locationIntent.setAction(LOCATION_ACTION);
    }


    @Override
    protected void onPreExecute() {
        isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!isGpsEnabled && !isNetworkEnabled) {
            try {
                Intent toBroadcast = (Intent) locationIntent.getExtras().get(LOCATION_EXTRA);
                toBroadcast.putExtra(LOCATION_ERROR, "Location provider disbaled");
                toBroadcast.putExtra(LOCATION_PROVIDER_DISABLED, true);
                toBroadcast.putExtra(LOCATION_CURRENT, getLastLocation());
                locationContext.sendBroadcast(toBroadcast);
            } catch (Exception e) {
                e.printStackTrace();
            }
            quit();
            return;
        }
        onTimeout = new Runnable() {
            public void run() {
                Intent toBroadcast = (Intent) locationIntent.getExtras().get(LOCATION_EXTRA);
                toBroadcast.putExtra(LOCATION_ERROR, "Timeout!");
                toBroadcast.putExtra(LOCATION_PROVIDER_DISABLED, false);
                toBroadcast.putExtra(LOCATION_CURRENT, getLastLocation());
                locationContext.sendBroadcast(toBroadcast);
                quit();
            }
        };

        handler.postDelayed(onTimeout, TIMEOUT);

        try {
            if (ActivityCompat.checkSelfPermission(locationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(locationContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_DISTANCE_CHANGE_FOR_UPDATES, MIN_TIME_BW_UPDATES, locationListenerGps);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_DISTANCE_CHANGE_FOR_UPDATES, MIN_TIME_BW_UPDATES, locationListenerNetwork);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            quit();
        }
    }

    /**
     * Called when the Handler loop ends. Removes the
     * location listener.
     */
    @Override
    protected void onPostExecute() {
        super.onPostExecute();
    }

    /**
     * Called when the WakeLock is completely unlocked.
     * Stops the service, so everything shuts down.
     */
    @Override
    protected void onUnlocked() {
        Intent serviceIntent = new Intent(locationContext, LocationService.class);
        locationContext.stopService(serviceIntent);
    }

    private Location getLastLocation() {
        Location wifiLocation = null, gpsLocation = null;
        if (ActivityCompat.checkSelfPermission(locationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(locationContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (isGpsEnabled) {
                gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
            if (isNetworkEnabled) {
                wifiLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }
        if (gpsLocation != null && wifiLocation != null) {
            if (gpsLocation.getTime() > wifiLocation.getTime()) {
                return gpsLocation;
            } else {
                return wifiLocation;
            }
        } else if (gpsLocation != null) {
            return gpsLocation;
        } else if (wifiLocation != null) {
            return wifiLocation;
        }
        return null;
    }

    /***
     * There are two listeners GPS AND NETWORK listener
     */
    private LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            Intent toBroadcast = (Intent) locationIntent.getExtras().get(LOCATION_EXTRA);
            toBroadcast.putExtra(LOCATION_CURRENT, location);
            toBroadcast.putExtra("LocationProvider Is", "GPS");
            locationContext.sendBroadcast(toBroadcast);
            if (ActivityCompat.checkSelfPermission(locationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(locationContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                quit();
                return;
            }
            locationManager.removeUpdates(this);
            locationManager.removeUpdates(locationListenerNetwork);
            handler.removeCallbacks(onTimeout);
            quit();
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };


    private LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            try {

                Intent toBroadcast = new Intent(locationIntent);
                toBroadcast.putExtra(LOCATION_CURRENT, location);
                toBroadcast.putExtra("LocationProvider Is", "NETWORK");
                locationContext.sendBroadcast(toBroadcast);
                if (ActivityCompat.checkSelfPermission(locationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(locationContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    handler.removeCallbacks(onTimeout);
                    quit();
                    return;
                }
                locationManager.removeUpdates(this);
                locationManager.removeUpdates(locationListenerGps);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                handler.removeCallbacks(onTimeout);
                quit();
            }
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };
}
