package com.akstudio.androtrack.restclient;

import com.akstudio.androtrack.model.CustomResponse;
import com.akstudio.androtrack.model.User;
import com.akstudio.androtrack.utils.Fields;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Asifbakht on 7/20/2016.
 */
public interface UserApi {

    @GET(Fields.GET)
    Call<CustomResponse> get();

    @GET("user/login")
    Call<CustomResponse> loginUser(
            @Query(Fields.EMAIL) String email,
            @Query(Fields.PASSWORD) String password
    );


    @FormUrlEncoded
    @POST("user/register")
    Call<CustomResponse> registerUser(
            @Body User userData
    );

    /*@POST(Fields.POST)
    Call<CustomResponse> postWithJson(
            @Body User loginData
    );*/
}
