package com.example.weddingelements_android.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.weddingelements_android.R;
import com.example.weddingelements_android.interfaces.AuthApi;
import com.example.weddingelements_android.interfaces.RestApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    Button loginBtn;
    Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = findViewById(R.id.login_btn_login);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loginUser(email.getText().toString(),password.getText().toString());

                startActivity(new Intent(getApplicationContext(),BusinessHomeActivity.class));
            }
        });
    }

    private void loginUser(String username, String password) {
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder().baseUrl(AuthApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();



        AuthApi api = retrofit.create(AuthApi.class);

        Call<JSONObject> call = api.login(username,password);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                System.out.println("Success");
                Object obj = response.body();
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                System.out.println("Failed");
                System.out.println(t);
            }
        });

    }
}