package com.akstudio.androtrack.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.akstudio.androtrack.services.AndroTrackService;

/**
 * Created by Asifbakht on 7/20/2016.
 */
public class Util {
    private static Context context;

    public Util(Context context) {
        this.context = context;
    }

    public static boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (AndroTrackService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    public static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;
        else
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public static void startTrackService(Context x){
        x.startService(new Intent(x, AndroTrackService.class));
    }
}
