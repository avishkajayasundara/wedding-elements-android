package com.example.weddingelements_android.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.weddingelements_android.R;
import com.example.weddingelements_android.adapters.AdvertisementAdapter;
import com.example.weddingelements_android.interfaces.RestApi;
import com.example.weddingelements_android.model.Advertisement;
import com.example.weddingelements_android.model.Cache;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home extends AppCompatActivity {

    Retrofit retrofit;
    FloatingActionButton floatingActionButton1;
    FloatingActionButton floatingActionButton2;
    FloatingActionButton floatingActionButton3;
    FloatingActionButton floatingActionButton4;
    RecyclerView recyclerView;
    AdvertisementAdapter adapter;
    List<Advertisement> advertisements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FirebaseApp.initializeApp(getApplicationContext());
        Paper.init(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.adv_recycler);
        floatingActionButton1 = findViewById(R.id.fab_myprof1);
        floatingActionButton2 = findViewById(R.id.fab_reviews);
        floatingActionButton3 = findViewById(R.id.fab_inquiry);
        floatingActionButton4 = findViewById(R.id.fab_logout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CustomerProfileActivity.class));
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CustomerReviewsActivity.class));
            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), InquiryActivity.class));
            }
        });
        floatingActionButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });


        retrofit = new Retrofit.Builder().baseUrl(RestApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestApi api = retrofit.create(RestApi.class);
        Call<List<Advertisement>> call = api.getAdvertisements();

        call.enqueue(new Callback<List<Advertisement>>() {
            @Override
            public void onResponse(Call<List<Advertisement>> call, Response<List<Advertisement>> response) {
                System.out.println("Success");
                advertisements = response.body();
                adapter = new AdvertisementAdapter(advertisements);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Advertisement>> call, Throwable t) {
                System.out.println("Failed");
            }
        });

    }

    private void logout() {
        retrofit = new Retrofit.Builder().baseUrl(RestApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestApi api = retrofit.create(RestApi.class);
        Call<Void> call = api.logoutUser(Cache.user.getUsername());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println("Success");
                Paper.book().delete("User");
                Cache.user = null;
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Netword Error",Toast.LENGTH_LONG);
            }
        });
    }
}