package com.akstudio.androtrack.services;

import android.os.HandlerThread;
import android.os.PowerManager;

/**
 * Created by Asifbakht on 7/24/2016.
 */
public class WakefulThread extends HandlerThread {
    public WakefulThread(String name) {
        super(name);
    }
    private PowerManager.WakeLock lock=null;


    public WakefulThread(PowerManager.WakeLock lock, String name) {
        super(name);

        this.lock=lock;
    }

    protected void onPostExecute() {
        if (lock.isHeld()) {
            lock.release();
        }

        if (!lock.isHeld()) {
            onUnlocked();
        }
    }

    protected void onUnlocked() {
        // no-op by default
    }

    @Override
    protected void onLooperPrepared() {
        try {
            onPreExecute();
        }
        catch (RuntimeException e) {
            onPostExecute();
            throw(e);
        }
    }

    protected void onPreExecute() {
        // no-op by default
    }

    @Override
    public void run() {
        try {
            super.run();
        }
        finally {
            onPostExecute();
        }
    }
}
