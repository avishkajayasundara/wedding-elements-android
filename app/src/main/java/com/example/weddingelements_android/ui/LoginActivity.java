package com.example.weddingelements_android.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.weddingelements_android.R;
import com.example.weddingelements_android.interfaces.AuthApi;
import com.example.weddingelements_android.interfaces.RestApi;
import com.example.weddingelements_android.model.Cache;
import com.example.weddingelements_android.model.LoggedInUser;
import com.example.weddingelements_android.model.LoginRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "Test";
    EditText email,password;
    Button loginBtn;
    Retrofit retrofit;
    String id = "Not Yet";
    CountDownLatch countDownLatch = new CountDownLatch(1);

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
                try {
                    loginUser(email.getText().toString(),password.getText().toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loginUser(String username, String password) throws InterruptedException {
        String token = "";
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        id = task.getResult();
                        retrofit = new Retrofit.Builder().baseUrl(RestApi.BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        RestApi api = retrofit.create(RestApi.class);
                        Call<LoggedInUser> call = api.loginUser(new LoginRequest(username,password,id));
                        call.enqueue(new Callback<LoggedInUser>() {
                            @Override
                            public void onResponse(Call<LoggedInUser> call, Response<LoggedInUser> response) {
                                LoggedInUser user = response.body();
                                Cache.user = user;
                                if(user.getUserRole()=="ADMIN"){
                                    startActivity(new Intent(getApplicationContext(),AdminHome.class));
                                }else{
                                    if(user.getUserRole()=="CUSTOMER"){
                                        startActivity(new Intent(getApplicationContext(),Home.class));
                                    }
                                    else{
                                        startActivity(new Intent(getApplicationContext(),BusinessHomeActivity.class));
                                    }
                                }

                            }

                            @Override
                            public void onFailure(Call<LoggedInUser> call, Throwable t) {
                                System.out.println("Failed");
                            }
                        });

                    }
                });

    }
}