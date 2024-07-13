package com.example.liveintutiontask;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface CallApiInterface {
    @POST
    Call<JsonObject> SendJSONRequestWithBodypost(@Url String url, @Body JsonObject jsonObject);
    @GET
    Call<JsonObject> SendJSONRequestWithBodyget(@Url String url, @Query("turf_id") int truf_id, @Query("date") String date);

    @POST
    Call<JsonObject> SendJSONRequestWithoutBodypost(@Url String url);

    @GET
    Call<JsonObject> SendJSONRequestWithoutBodyget(@Url String url);
}
