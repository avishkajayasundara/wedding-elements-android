package com.example.weddingelements_android.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.weddingelements_android.R;
import com.example.weddingelements_android.adapters.AdvertisementAdapter;
import com.example.weddingelements_android.interfaces.RestApi;
import com.example.weddingelements_android.model.BusinessOwnerMobile;
import com.example.weddingelements_android.model.BusinessUpdateRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BusinessHomeActivity extends AppCompatActivity {
    EditText name,address,contact,category,country,password,email,description;
    AppCompatButton button;
    Retrofit retrofit;
    TextView topText;
    RecyclerView recyclerView;
    AdvertisementAdapter adapter;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_home);
        floatingActionButton = findViewById(R.id.busfloatingActionButton);
        topText = findViewById(R.id.profb_name_top);
        name = findViewById(R.id.profb_name);
        email = findViewById(R.id.profb_email);
        address = findViewById(R.id.profb_address);
        contact = findViewById(R.id.profb_contact);
        category = findViewById(R.id.profb_category);
        password = findViewById(R.id.profb_password);
        description = findViewById(R.id.profb_description);
        button = findViewById(R.id.profb_update);
        recyclerView = findViewById(R.id.business_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        retrofit = new Retrofit.Builder().baseUrl(RestApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getProfileDetails();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestApi api = retrofit.create(RestApi.class);
                BusinessUpdateRequest request = new BusinessUpdateRequest(
                       email.getText().toString(), name.getText().toString(),address.getText().toString(),
                        contact.getText().toString(),description.getText().toString()
                );
                Call<Void> call = api.updateBusinessDetails(request);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        System.out.println("Successful");
                        finish();
                        startActivity(getIntent());
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        System.out.println("Failed");
                    }
                });
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddAdvertisementActivity.class));
            }
        });

    }

    private void getProfileDetails() {

        RestApi api = retrofit.create(RestApi.class);
        retrofit2.Call<BusinessOwnerMobile> call = api.getBusinessOwner("aa@gmail.com");
        call.enqueue(new Callback<BusinessOwnerMobile>() {
            @Override
            public void onResponse(Call<BusinessOwnerMobile> call, Response<BusinessOwnerMobile> response) {
                BusinessOwnerMobile businessOwner = response.body();
                name.setText(businessOwner.getBusinessOwner().getName());
                topText.setText(businessOwner.getBusinessOwner().getName());
                email.setText(businessOwner.getBusinessOwner().getEmail());
                contact.setText(businessOwner.getBusinessOwner().getContactNo());
                category.setText(businessOwner.getBusinessOwner().getBusinessType());
                address.setText(businessOwner.getBusinessOwner().getAddress());
                password.setText(businessOwner.getBusinessOwner().getPassword());
                description.setText(businessOwner.getBusinessOwner().getDescription());
                adapter = new AdvertisementAdapter(businessOwner.getAdvertisements());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<BusinessOwnerMobile> call, Throwable t) {
                System.out.println("Failed");
            }
        });
    }
}