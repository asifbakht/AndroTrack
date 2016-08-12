package com.akstudio.androtrack.track;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;

import com.akstudio.androtrack.model.Call;
import com.akstudio.androtrack.storage.DatabaseHelper;
import com.akstudio.androtrack.utils.Fields;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Asifbakht on 7/30/2016.
 */
public class CallTrack extends ContentObserver {

    private static final String UNKNOWN = "Unknown";
    private static final String DATE_FORMAT_TAG = "yyyy-MM-dd HH:mm:ss";
    private static String CALL_LAST_ID;
    private Context context;
    private String[] projection;

    public CallTrack(Handler handler) {
        super(handler);
    }

    public CallTrack(Handler handler, Context context) {
        super(handler);
        this.context = context;
        projection = new String[]{CallLog.Calls._ID, CallLog.Calls.TYPE, CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME, CallLog.Calls.DURATION, CallLog.Calls.DATE};
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        checkChangeForCall();
    }

    @Override
    public boolean deliverSelfNotifications() {
        return true;
    }


    protected void checkChangeForCall() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, projection, null, null, CallLog.Calls.DATE + " DESC");
        String number, name, duration, id, type = "", dateTime;
        int callType;
        Date dateInstance;
        try {
            if (cursor.moveToFirst()) {
                id = cursor.getString(cursor.getColumnIndex(projection[0]));
                if (id.equals(CALL_LAST_ID)) {
                    cursor.close();
                    return;
                }
                callType = cursor.getInt(cursor.getColumnIndex(projection[1]));
                number = cursor.getString(cursor.getColumnIndex(projection[2]));
                name = cursor.getString(cursor.getColumnIndex(projection[3]));
                duration = cursor.getString(cursor.getColumnIndex(projection[4]));
                dateTime = cursor.getString(cursor.getColumnIndex(projection[5]));

                switch (callType) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        type = Fields.OUTGOING;
                        break;

                    case CallLog.Calls.INCOMING_TYPE:
                        type = Fields.INCOMING;
                        break;

                    case CallLog.Calls.MISSED_TYPE:
                        type = Fields.MISSED;
                        break;

                    case CallLog.Calls.REJECTED_TYPE:
                        type = Fields.CANCELLED;
                        break;
                }
                dateInstance = new Date(Long.valueOf(dateTime));
                if (name == null) {
                    name = UNKNOWN;
                }

                String date = new SimpleDateFormat(DATE_FORMAT_TAG).format(dateInstance);
                CALL_LAST_ID = id;
                Call callObject = new Call(type, duration.toString(), number.toString(), date, name, Integer.valueOf(CALL_LAST_ID));
                DatabaseHelper.getInstance(context).getCallDao().create(callObject);
            }
        } catch (Exception eg) {
            eg.printStackTrace();
        }
        cursor.close();
    }
}
