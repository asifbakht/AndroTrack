package com.akstudio.androtrack.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.akstudio.androtrack.utils.Fields;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Asifbakht on 7/20/2016.
 */
public class PreferenceManager {

    private static SharedPreferences sharedPreference;
    private static SharedPreferences.Editor shareEdtior;
    private static Context prefsContext;


    public PreferenceManager(Context eg) {
        prefsContext = eg;
        sharedPreference = android.preference.PreferenceManager.getDefaultSharedPreferences(prefsContext);
        shareEdtior = sharedPreference.edit();
        if (!getBoolean(Fields.LAUNCH_FIRST)) {
            this.setBoolean(Fields.LAUNCH_FIRST, true);
            this.setBoolean(Fields.IS_LOGIN, false);
            this.setValue(Fields.SESSION, null);
            this.setBoolean(Fields.TRACK_CALL, false);
            this.setBoolean(Fields.TRACK_LOCATION, false);
            this.setBoolean(Fields.TRACK_SMS, false);
            this.setBoolean(Fields.REMEMBER_USER, false);
            this.setValue(Fields.SYNC_DATE, DateFormat.getDateTimeInstance().format(new Date()));
            this.setBoolean(Fields.LAUNCH_COMPLETE, true);
        }
    }

    public void setValue(String key, String value) {
        shareEdtior.putString(key, value);
        shareEdtior.commit();
    }

    public void setBoolean(String key, boolean value) {
        shareEdtior.putBoolean(key, value);
        shareEdtior.commit();
    }


    public String getValue(String key) {
        String value = sharedPreference.getString(key, null);
        return value;
    }

    public boolean getBoolean(String key) {
        boolean value = sharedPreference.getBoolean(key, false);
        return value;
    }
}
