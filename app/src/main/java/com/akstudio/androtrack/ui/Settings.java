package com.akstudio.androtrack.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import com.akstudio.androtrack.R;
import com.akstudio.androtrack.services.AndroTrackService;
import com.akstudio.androtrack.utils.Fields;
import com.akstudio.androtrack.utils.Util;

/**
 * Created by Asifbakht on 7/20/2016.
 */
public class Settings extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            onCreatePreferenceActivity(this);
        } else {
            onCreatePreferenceFragment();
        }
    }

    /**
     * Wraps legacy {@link #onCreate(Bundle)} code for Android < 3 (i.e. API lvl
     * < 11).
     */
    @SuppressWarnings("deprecation")
    private void onCreatePreferenceActivity(Context x) {
        addPreferencesFromResource(R.xml.settings);
    }

    /**
     * Wraps {@link #onCreate(Bundle)} code for Android >= 3 (i.e. API lvl >=
     * 11).
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void onCreatePreferenceFragment() {
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new MyPreferenceFragment())
                .commit();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        boolean isRunning = Util.isServiceRunning();
        if (isRunning) {
            this.stopService(new Intent(this, AndroTrackService.class));
        }
        this.startService(new Intent(this, AndroTrackService.class));
    }


    public static class MyPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);
            final Preference prefCall = (Preference) findPreference(Fields.TRACK_CALL);
            final Preference prefLocation = (Preference) findPreference(Fields.TRACK_LOCATION);
            final Preference prefSms = (Preference) findPreference(Fields.TRACK_SMS);
            prefCall.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    runService(prefCall.getContext());
                    return false;
                }
            });

            prefLocation.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    runService(prefLocation.getContext());
                    return false;
                }
            });

            prefSms.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    runService(prefSms.getContext());
                    return false;
                }
            });

        }

        public void runService(Context x) {
            boolean isRunning = Util.isServiceRunning();
            if (isRunning) {
                x.stopService(new Intent(x, AndroTrackService.class));
            }
            x.startService(new Intent(x, AndroTrackService.class));
        }
    }
}
