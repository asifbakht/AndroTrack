package com.akstudio.androtrack.track;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;

import com.akstudio.androtrack.model.Sms;
import com.akstudio.androtrack.storage.DatabaseHelper;
import com.akstudio.androtrack.utils.Fields;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Asifbakht on 7/30/2016.
 */
public class SmsTrack extends ContentObserver {

    private static int lastSmsId;
    private final String DATE_FORMAT_TAG = "yyyy-MM-dd HH:mm:ss";
    private final String URI_PARSE_LINK = "content://sms/";

    private Context context;

    public SmsTrack(Handler handler, Context context) {
        super(handler);
        this.context = context;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        checkSmsForChange();
    }

    protected void checkSmsForChange() {
        Uri uriSms = Uri.parse(URI_PARSE_LINK);
        Cursor cursorInstance = context.getContentResolver().query(uriSms, null, null, null, null);
        cursorInstance.moveToNext(); // this will make it point to the first record, which is the last SMS sent

        int msgId = cursorInstance.getInt(cursorInstance.getColumnIndex(Fields.MSG_ID_TAG));
        if (msgId == lastSmsId) {
            cursorInstance.close();
            return;
        }
        String type = Fields.SENT;
        String body = cursorInstance.getString(cursorInstance.getColumnIndex(Fields.BODY)); //content of sms
        String phone = cursorInstance.getString(cursorInstance.getColumnIndex(Fields.ADDRESS)); //phone num
        String time = cursorInstance.getString(cursorInstance.getColumnIndex(Fields.DATE)); //date
        String protocol = cursorInstance.getString(cursorInstance.getColumnIndex(Fields.PROTOCOL)); //protocol
        String personName = phone;
        Date dateInstance = new Date(Long.valueOf(time));


        /**
         *
         * This portion is used to lookup for the name from address book and save the name of person into personName dataType
         */
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(personName));
        Cursor cs = context.getContentResolver().query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, ContactsContract.PhoneLookup.NUMBER + "='" + personName + "'", null, null);
        if (cs.getCount() > 0) {
            cs.moveToFirst();
            personName = cs.getString(cs.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }
        cs.close();
        cursorInstance.close();

        String date = new SimpleDateFormat(DATE_FORMAT_TAG).format(dateInstance);
        if (protocol == null)
            type = Fields.RECEIVE;

        Sms smsObject = new Sms(type, body, phone, date, personName, msgId);
        try {
            DatabaseHelper.getInstance(context).getSmsDAO().create(smsObject);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        lastSmsId = msgId;
    }
}
