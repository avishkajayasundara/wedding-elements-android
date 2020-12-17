package com.example.weddingelements_android.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.weddingelements_android.R;
import com.example.weddingelements_android.interfaces.RestApi;
import com.example.weddingelements_android.model.Inquiry;
import com.google.gson.Gson;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InquiryActivity extends AppCompatActivity {

    EditText name,email,contact,subject,message;
    AppCompatButton button;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry);
        name = findViewById(R.id.inq_name);
        email = findViewById(R.id.inq_email);
        contact = findViewById(R.id.inq_contact);
        subject = findViewById(R.id.inq_sub);
        message = findViewById(R.id.inq_message);
        button = findViewById(R.id.inq_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Inquiry inquiry = new Inquiry();
                inquiry.setInquiryId(UUID.randomUUID().toString());
                inquiry.setName(name.getText().toString());
                inquiry.setEmail(email.getText().toString());
                inquiry.setContactNo(contact.getText().toString());
                inquiry.setSubject(subject.getText().toString());
                inquiry.setMessage(message.getText().toString());
                sendInquiry(inquiry, v);
            }
        });
    }

    private void sendInquiry(Inquiry inquiry, View v) {
        retrofit = new Retrofit.Builder().baseUrl(RestApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RestApi api = retrofit.create(RestApi.class);
        Call<Inquiry> call = api.sendInquiry(inquiry);
        Gson gson = new Gson();
        System.out.println(gson.toJson(inquiry));
        call.enqueue(new Callback<Inquiry>() {
            @Override
            public void onResponse(Call<Inquiry> call, Response<Inquiry> response) {
                startActivity(new Intent(getApplicationContext(), Home.class));
            }

            @Override
            public void onFailure(Call<Inquiry> call, Throwable t) {
                System.out.println("Failed");
            }
        });

    }
}