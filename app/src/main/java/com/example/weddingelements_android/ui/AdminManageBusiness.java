package com.example.weddingelements_android.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.weddingelements_android.R;
import com.example.weddingelements_android.adapters.AdminCustomerAdapter;
import com.example.weddingelements_android.interfaces.RestApi;
import com.example.weddingelements_android.model.BusinessOwner;
import com.example.weddingelements_android.model.Customer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminManageBusiness extends AppCompatActivity {

    Retrofit retrofit;
    AdminCustomerAdapter adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_business);
        recyclerView = findViewById(R.id.bus_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        retrofit = new Retrofit.Builder().baseUrl(RestApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RestApi api = retrofit.create(RestApi.class);
        Call<List<BusinessOwner>> call = api.listBusinesses();
        call.enqueue(new Callback<List<BusinessOwner>>() {
            @Override
            public void onResponse(Call<List<BusinessOwner>> call, Response<List<BusinessOwner>> response) {
                System.out.println("Success");
                adapter = new AdminCustomerAdapter(response.body(),false);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<BusinessOwner>> call, Throwable t) {
                System.out.println("Failed");
            }
        });
    }
}