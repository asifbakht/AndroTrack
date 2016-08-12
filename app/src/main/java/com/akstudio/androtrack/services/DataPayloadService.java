package com.akstudio.androtrack.services;

import android.app.IntentService;
import android.content.Intent;

import com.akstudio.androtrack.model.Location;
import com.akstudio.androtrack.model.Sms;
import com.akstudio.androtrack.restclient.Api;
import com.akstudio.androtrack.restclient.Payload;
import com.akstudio.androtrack.storage.DatabaseHelper;

import java.util.List;

/**
 * Created by Asifbakht on 8/6/2016.
 */
public class DataPayloadService extends IntentService {
    private final String PAYLOAD = "com.akstudio.track.PAY_LOAD";

    public DataPayloadService(String name) {
        super(name);
    }

    public DataPayloadService() {
        super("DataUploader");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent.getAction().equals(PAYLOAD)) {
            Payload service = Api.getInstance().getRetrofit().create(Payload.class);
            List<Sms> smsList = DatabaseHelper.getInstance(this).getSmsList();
            List<com.akstudio.androtrack.model.Call> callList = DatabaseHelper.getInstance(this).getCallList();
            List<Location> locationList = DatabaseHelper.getInstance(this).getLocationList();
            service.postPayload(smsList, locationList, callList, null);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
