package com.example.liveintutiontask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

public class ShowTrufs extends AppCompatActivity {

    static {
        System.loadLibrary("native-lib");
    }
    List<LoadClassItem> data = new ArrayList<>();
    private RecyclerView trufdetails;
    private MyAdapterTruf mAdapter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_trufs);
        callTestApi();
    }

    private void callTestApi() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(ShowTrufs.this);
        }
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        if (!ShowTrufs.this.isFinishing()) {
            progressDialog.show();
        }
        Retrofit retrofit = ApiClient.getApiClient();
        CallApiInterface apiInterface = retrofit.create(CallApiInterface.class);
        Call<JsonObject> call = apiInterface.SendJSONRequestWithBodyalltrufs(ConfigForAPIURL.AllTurfsDetails,"80b856a2acc361a849858e8123ccef26bef7452f11403072024160737");
        call.enqueue(new Callback<JsonObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                JsonObject responseData = response.body();
                if (response.code() == 200) {
                    JsonElement jsonElement=responseData.get("response");
                    JsonArray jsonArray = new JsonParser().parse(String.valueOf(jsonElement)).getAsJsonArray();
                    for(int i=0;i<jsonArray.size();i++)
                    {
                        JsonObject jsonObject=jsonArray.get(i).getAsJsonObject();
                        LoadClassItem loadData = new LoadClassItem();
                        loadData.VenueId=jsonObject.get("venue_id").toString();
                        loadData.VentureId=jsonObject.get("venture_id").toString();
                        loadData.Name=jsonObject.get("name").toString();
                        loadData.Icon=jsonObject.get("icon").toString();
                        JsonElement categoryElement=jsonObject.get("categories");
                        JsonArray jsonCategory = new JsonParser().parse(String.valueOf(categoryElement)).getAsJsonArray();
                        for(int j=0;j<jsonCategory.size();j++)
                        {
                            JsonObject categoryObj=jsonCategory.get(j).getAsJsonObject();
                            loadData.CatetoryId=categoryObj.get("id").toString();
                            loadData.CatetoryName=categoryObj.get("name").toString();
                            loadData.VenuePropertyDescription=categoryObj.get("venue_property_description").toString();
                            JsonElement trufsElement=categoryObj.get("turfs");
                            JsonArray jsontrufs = new JsonParser().parse(String.valueOf(trufsElement)).getAsJsonArray();
                            for(int k=0;k<jsontrufs.size();k++){
                                JsonObject trufsObj=jsontrufs.get(k).getAsJsonObject();
                                loadData.TrufsId=trufsObj.get("id").toString();
                                loadData.TrufsName=trufsObj.get("name").toString();
                                loadData.TrufsCategory=trufsObj.get("category_id").toString();
                            }
                        }
                        data.add(loadData);
                        trufdetails = findViewById(R.id.trufdetails);
                        mAdapter = new MyAdapterTruf(ShowTrufs.this, data);
                        trufdetails.setAdapter(mAdapter);
                        trufdetails.setLayoutManager(new LinearLayoutManager(ShowTrufs.this));
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable error) {
                Log.d("API Response2",""+error);
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
            View view = LayoutInflater.from(context).inflate(R.layout.mytrufsactivitydetails, parent, false);
            return new MyAdapterTruf.MyHolder(view);
        }

        @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            MyAdapterTruf.MyHolder myHolder = (MyAdapterTruf.MyHolder) holder;
            myHolder.venueId.setText(data.get(position).VenueId.replace("\"", ""));
            myHolder.ventrueId.setText(data.get(position).VentureId.replace("\"", ""));
            myHolder.name.setText(data.get(position).Name.replace("\"", ""));
            myHolder.categroyId.setText(data.get(position).CatetoryId.replace("\"", ""));
            myHolder.categroyName.setText(data.get(position).CatetoryName.replace("\"", ""));
            myHolder.categroyDescription.setText(data.get(position).VenuePropertyDescription.replace("\"", ""));
            myHolder.trufId.setText(data.get(position).TrufsId.replace("\"", ""));
            myHolder.trufName.setText(data.get(position).TrufsName.replace("\"", ""));
            myHolder.trufCategoryId.setText(data.get(position).TrufsCategory.replace("\"", ""));
            Glide.with(ShowTrufs.this.getApplicationContext()).load(ConfigForAPIURL.IconBaseUrl+data.get(position).Icon.replace("\"", "")).into(myHolder.iconContainer);
        }


        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {
            private TextView venueId;
            private TextView ventrueId;
            private TextView name;
            private TextView categroyId;
            private TextView categroyName;
            private TextView categroyDescription;
            private TextView trufId;
            private TextView trufName;
            private TextView trufCategoryId;
            private ImageView iconContainer;
            public MyHolder(View itemView) {
                super(itemView);
                venueId = (TextView) itemView.findViewById(R.id.venueId);
                ventrueId = (TextView) itemView.findViewById(R.id.ventrueId);
                name = (TextView) itemView.findViewById(R.id.name);
                categroyId = (TextView) itemView.findViewById(R.id.categroyId);
                categroyName = (TextView) itemView.findViewById(R.id.categroyName);
                categroyDescription = (TextView) itemView.findViewById(R.id.categroyDescription);
                trufId = (TextView) itemView.findViewById(R.id.trufId);
                trufName = (TextView) itemView.findViewById(R.id.trufName);
                trufCategoryId = (TextView) itemView.findViewById(R.id.trufCategoryId);
                iconContainer= (ImageView) itemView.findViewById(R.id.icon_container);
            }
        }
    }
}