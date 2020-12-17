package com.example.weddingelements_android.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.weddingelements_android.R;
import com.example.weddingelements_android.adapters.ReviewAdapter;
import com.example.weddingelements_android.interfaces.RestApi;
import com.example.weddingelements_android.model.Review;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomerReviewsActivity extends AppCompatActivity {

    List<Review> reviews;
    RecyclerView recyclerView;
    ReviewAdapter adapter;
    Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_reviews);
        recyclerView = findViewById(R.id.cus_rev_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        retrofit = new Retrofit.Builder().baseUrl(RestApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestApi api = retrofit.create(RestApi.class);
        Call<List<Review>> call = api.getReviewsByCustomer("w@gmail.com");
        call.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                System.out.println("Success");
                reviews = response.body();
                adapter = new ReviewAdapter(reviews,false);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                System.out.println("Failed");

            }
        });



    }
}