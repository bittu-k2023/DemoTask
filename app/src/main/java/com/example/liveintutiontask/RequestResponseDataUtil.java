package com.example.liveintutiontask;

import com.google.gson.JsonObject;

public class RequestResponseDataUtil {

    public JsonObject trufDetails(int trufvalue,String trufdate) {
        JsonObject requestJson = new JsonObject();
        requestJson.addProperty("turf_id", trufvalue);
        requestJson.addProperty("date", trufdate);
        return requestJson;
    }
}
