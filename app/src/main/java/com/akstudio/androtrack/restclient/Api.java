package com.akstudio.androtrack.restclient;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Asifbakht on 7/20/2016.
 */
public class Api {

    private static final String API_URL = "http://desksort.ae:8001/DesksortDev/";
    static Api apiManger = null;
    Retrofit retrofit = null;

    public Api() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public static Api getInstance() {
        if (apiManger == null) {
            apiManger = new Api();
        }
        return apiManger;
    }

    public Retrofit getRetrofit() {
        return this.retrofit;
    }
}

