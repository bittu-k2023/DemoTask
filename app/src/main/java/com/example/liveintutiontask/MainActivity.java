package com.example.liveintutiontask;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("native-lib");
    }

    List<LoadClassItem> data = new ArrayList<>();
    ProgressDialog progressDialog;
    private RecyclerView trufdetails;
    private MyAdapterTruf mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callTestApi();
    }

    private void callTestApi() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(MainActivity.this);
        }
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        if (!MainActivity.this.isFinishing()) {
            progressDialog.show();
        }
        Retrofit retrofit = ApiClient.getApiClient();
        CallApiInterface apiInterface = retrofit.create(CallApiInterface.class);
        Call<JsonObject> call = apiInterface.SendJSONRequestWithBodyget(ConfigForAPIURL.AllTurfs, 1, "2024-07-13", "80b856a2acc361a849858e8123ccef26bef7452f11403072024160737", "application/json", "application/json");
        call.enqueue(new Callback<JsonObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                JsonObject responseData = response.body();
                if (response.code() == 200) {
                    JsonElement jsonElement = responseData.get("response");
                    JsonArray jsonArray = new JsonParser().parse(String.valueOf(jsonElement)).getAsJsonArray();
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                        LoadClassItem loadData = new LoadClassItem();
                        loadData.startdate = jsonObject.get("start_time").toString();
                        loadData.enddate = jsonObject.get("end_time").toString();
                        loadData.baseprice = jsonObject.get("base_price").toString();
                        loadData.available = jsonObject.get("available").toString();
                        data.add(loadData);
                        trufdetails = findViewById(R.id.trufdetails);
                        mAdapter = new MyAdapterTruf(MainActivity.this, data);
                        trufdetails.setAdapter(mAdapter);
                        trufdetails.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable error) {
                Log.d("API Response2", "" + error);
            }
        });
    }

    private class MyAdapterTruf extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final Context context;
        List<LoadClassItem> data;

        public MyAdapterTruf(Context context, List<LoadClassItem> data) {
            this.context = context;
            this.data = data;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.myturflayout, parent, false);
            return new MyHolder(view);
        }

        @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            MyHolder myHolder = (MyHolder) holder;
            myHolder.startdate.setText(data.get(position).startdate.replace("\"", ""));
            myHolder.enddate.setText(data.get(position).enddate.replace("\"", ""));
            myHolder.price.setText(data.get(position).baseprice.replace("\"", ""));
            myHolder.avalible.setText(data.get(position).available.replace("\"", ""));
            if (data.get(position).available.replace("\"", "").equals("false")) {
                myHolder.bookslots.setVisibility(View.GONE);
            } else {
                myHolder.bookslots.setVisibility(View.VISIBLE);
            }
            myHolder.bookslots.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bookmyslot();
                }

                private void bookmyslot() {
                    if (progressDialog == null) {
                        progressDialog = new ProgressDialog(MainActivity.this);
                    }
                    progressDialog.setCancelable(false);
                    progressDialog.setMessage("Please Wait");
                    if (!MainActivity.this.isFinishing()) {
                        progressDialog.show();
                    }

                    String Startdate = data.get(position).startdate.replace("\"", "");
                    String Enddate = data.get(position).enddate.replace("\"", "");
                    int TrufId = 20;
                    JsonArray slots = new JsonArray();
                    JsonObject object = new JsonObject();
                    object.addProperty("turf_id", TrufId);
                    object.addProperty("start_time", Startdate);
                    object.addProperty("end_time", Enddate);
                    slots.add(object);
                    String user_first_name = "test";
                    String user_last_name = "user";
                    String user_email = "tu1@gmail.com";
                    String user_dial_code = "+91";
                    String user_mobile_no = "9999999995";
                    JsonObject requestData = new RequestResponseDataUtil().Bookslots(slots, user_first_name, user_last_name, user_email, user_dial_code, user_mobile_no);
                    Retrofit retrofit = ApiClient.getApiClient();
                    CallApiInterface apiInterface = retrofit.create(CallApiInterface.class);
                    Call<JsonObject> call = apiInterface.SendJSONRequestWithBodypost(ConfigForAPIURL.BookSlots, requestData,"80b856a2acc361a849858e8123ccef26bef7452f11403072024160737");
                    call.enqueue(new Callback<JsonObject>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            JsonObject responseData = response.body();
                            if (response.code() == 200) {
                                Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable error) {
                            Log.d("API Response2", "" + error);
                        }
                    });
                }
            });
        }


        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {
            private TextView startdate;
            private TextView enddate;
            private TextView price;
            private TextView avalible;
            private Button bookslots;

            public MyHolder(View itemView) {
                super(itemView);

                startdate = (TextView) itemView.findViewById(R.id.startdate);
                enddate = (TextView) itemView.findViewById(R.id.enddate);
                price = (TextView) itemView.findViewById(R.id.price);
                avalible = (TextView) itemView.findViewById(R.id.avalible);
                bookslots = (Button) itemView.findViewById(R.id.bookslots);

            }
        }
    }
}