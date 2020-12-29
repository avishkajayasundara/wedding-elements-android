package com.example.weddingelements_android.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.weddingelements_android.R;
import com.example.weddingelements_android.adapters.ReviewAdapter;
import com.example.weddingelements_android.interfaces.RestApi;
import com.example.weddingelements_android.model.Advertisement;
import com.example.weddingelements_android.model.LoggedInUser;
import com.example.weddingelements_android.model.Review;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SIngleAdvertisementActivity extends AppCompatActivity {
    Advertisement advertisement;
    Gson gson;
    private AppCompatButton delBtn;
    private TextView title;
    private TextView startingPrice;
    private ImageView image;
    private RatingBar ratingBar;
    private TextView ratingText;
    private EditText description;
    private float rating;
    private RecyclerView recyclerView;
    private Retrofit retrofit;
    private List<Review> reviews;
    private ReviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_ingle_advertisement);
        delBtn = findViewById(R.id.adv_del);
        recyclerView = (RecyclerView) findViewById(R.id.singadv_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        title = findViewById(R.id.sin_title);
        startingPrice = findViewById(R.id.sin_price);
        image = findViewById(R.id.sin_image);
        ratingText = findViewById(R.id.sing_rating);
        ratingBar = findViewById(R.id.sing_ratingbar);
        description = findViewById(R.id.sin_description);
        gson = new Gson();
        advertisement = gson.fromJson(getIntent().getStringExtra("Advertisement"), Advertisement.class);
        rating = advertisement.getScore()/advertisement.getNumberOfReviews();
        ratingBar.setRating(rating/2);
        ratingText.setText(String.valueOf(rating));
        description.setText(advertisement.getDescription());
        startingPrice.setText("Starting From Rs."+advertisement.getStartingPrice());
        title.setText(advertisement.getTitle());
        Picasso.get().load(advertisement.getImage()).into(image);
        retrofit = new Retrofit.Builder().baseUrl(RestApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestApi api = retrofit.create(RestApi.class);
        if(LoggedInUser.getInstance().getUserRole() == "BUSINESS_OWNER"){
            delBtn.setVisibility(View.VISIBLE);
            delBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Call<Void> call = api.deleteAdvertisement(advertisement.getAdvertisementId());
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            System.out.println("deleted Successfully");
                            startActivity(new Intent(getApplicationContext(),BusinessHomeActivity.class));
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            System.out.println("Failed");
                        }
                    });
                }
            });
        }
        Call<List<Review>> call = api.getReviewsByAdvertisement(advertisement.getAdvertisementId());

        call.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                System.out.println("Success");
                reviews = response.body();
                adapter = new ReviewAdapter(reviews,true);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                System.out.println("Failure");
            }
        });

    }
}