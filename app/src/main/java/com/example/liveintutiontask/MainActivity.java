package com.example.liveintutiontask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Callback;


public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("native-lib");
    }
    TextView callapi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callapi=findViewById(R.id.callapi);
        callapi.setOnClickListener(view -> callTestApi());

    }
    private void callTestApi() {

        JsonObject requestData = new RequestResponseDataUtil().trufDetails(1,"2024-07-13");
        Retrofit retrofit = ApiClient.getApiClient();
        CallApiInterface apiInterface = retrofit.create(CallApiInterface.class);
        Call<JsonObject> call = apiInterface.SendJSONRequestWithBodyget(ConfigForAPIURL.AllTurfs,1,"2024-07-13");
        call.enqueue(new Callback<JsonObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                JsonObject responseData = response.body();
                Log.d("API Response1",""+response);
//                if (response.code() == 200) {
//                    Log.d("MyTrufs",)
//                } else {
//
//                }
            }
            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable error) {
                Log.d("API Response2",""+error);
            }
        });
    }
}