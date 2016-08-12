package com.akstudio.androtrack.restclient;

import com.akstudio.androtrack.model.CustomResponse;
import com.akstudio.androtrack.model.Location;
import com.akstudio.androtrack.model.Sms;
import com.akstudio.androtrack.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Asifbakht on 8/6/2016.
 */
public interface Payload {

    @POST("payload/upload")
    Call<CustomResponse> postPayload(
            @Body List<Sms> smsData,
            @Body List<Location> locationData,
            @Body List<com.akstudio.androtrack.model.Call> callData,
            @Body User userData
    );
}
