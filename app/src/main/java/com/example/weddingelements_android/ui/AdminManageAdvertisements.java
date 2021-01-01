package com.example.weddingelements_android.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.weddingelements_android.R;
import com.example.weddingelements_android.adapters.AdvertisementAdapter;
import com.example.weddingelements_android.interfaces.RestApi;
import com.example.weddingelements_android.model.Advertisement;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminManageAdvertisements extends AppCompatActivity {
    Retrofit retrofit;
    RecyclerView recyclerView;
    AdvertisementAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_advertisements);
        recyclerView = findViewById(R.id.admin_adv_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        retrofit = new Retrofit.Builder().baseUrl(RestApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestApi api = retrofit.create(RestApi.class);
        Call<List<Advertisement>> call = api.getAdvertisements();

        call.enqueue(new Callback<List<Advertisement>>() {
            @Override
            public void onResponse(Call<List<Advertisement>> call, Response<List<Advertisement>> response) {
                System.out.println("Success");
                adapter = new AdvertisementAdapter(response.body());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Advertisement>> call, Throwable t) {
                System.out.println("Failed");
            }
        });
    }
}