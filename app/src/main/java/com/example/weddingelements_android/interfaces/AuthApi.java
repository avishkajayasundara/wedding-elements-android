package com.example.weddingelements_android.interfaces;

import com.example.weddingelements_android.model.Advertisement;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthApi {
    static String BASE_URL = "http://192.168.1.5.103:8080/";

    @FormUrlEncoded
    @POST("login")
    Call<JSONObject> login(@Field("username") String username, @Field("password") String password);

}
