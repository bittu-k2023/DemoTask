package com.example.liveintutiontask;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class RequestResponseDataUtil {

    public JsonObject trufDetails(int trufvalue,String trufdate) {
        JsonObject requestJson = new JsonObject();
        requestJson.addProperty("turf_id", trufvalue);
        requestJson.addProperty("date", trufdate);
        return requestJson;
    }

    public JsonObject Bookslots(JsonArray  slots, String fname,String lname, String email,String dialcode, String mob) {
        JsonObject requestJson = new JsonObject();
        requestJson.add("slots", slots);
        requestJson.addProperty("user_first_name", fname);
        requestJson.addProperty("user_last_name", lname);
        requestJson.addProperty("user_email", email);
        requestJson.addProperty("user_dial_code", dialcode);
        requestJson.addProperty("user_mobile_no", mob);
        return requestJson;
    }
}
