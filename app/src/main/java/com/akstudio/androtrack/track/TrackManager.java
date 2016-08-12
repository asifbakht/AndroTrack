package com.akstudio.androtrack.track;

import android.content.Context;
import android.os.Handler;

/**
 * Created by Asifbakht on 7/24/2016.
 */
public class TrackManager {

    private static TrackManager instanceObject = null;
    public SmsTrack smsObserver;
    public CallTrack callObserver;
    Context context;

    public TrackManager(Context context) {
        this.context = context;
        smsObserver = new SmsTrack(new Handler(), context);
        callObserver = new CallTrack(new Handler(), context);
    }

    public static TrackManager getInstance(Context ctx) {
        if (instanceObject == null) {
            instanceObject = new TrackManager(ctx);
        }
        return instanceObject;
    }

    public void resetInstance(Context ctx) {
        if (instanceObject != null) {
            instanceObject = new TrackManager(ctx);
        }
    }

}