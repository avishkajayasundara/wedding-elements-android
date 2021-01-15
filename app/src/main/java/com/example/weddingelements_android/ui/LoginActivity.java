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
import com.example.weddingelements_android.util.SignatureValidatior;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

import io.paperdb.Paper;
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
    String id = "";
    HashMap<String,String> map = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Paper.init(getApplicationContext());
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
        try {
            map = SignatureValidatior.getKeyPair();
        } catch (NoSuchAlgorithmException e) {
            Toast.makeText(getApplicationContext(),"Something Went Wrong", Toast.LENGTH_LONG);
        }
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
                        LoginRequest loginRequest = new LoginRequest(username,password,id,map.get("public"));
                        Call<LoggedInUser> call = api.loginUser(loginRequest);
                        call.enqueue(new Callback<LoggedInUser>() {
                            @Override
                            public void onResponse(Call<LoggedInUser> call, Response<LoggedInUser> response) {
                                LoggedInUser user = response.body();
                                if(user==null){
                                    Toast.makeText(getApplicationContext(),"Invalid Login Credentials",Toast.LENGTH_LONG);
                                }else{
                                    user.setKey(map.get("private"));
                                    Paper.book().write("User", user);
                                    Cache.user = user;
                                    if(user.getUserRole().equals("ADMIN")){
                                        startActivity(new Intent(getApplicationContext(),AdminHome.class));
                                    }else{
                                        if(user.getUserRole().equals("CUSTOMER")){
                                            startActivity(new Intent(getApplicationContext(),Home.class));
                                        }
                                        else{
                                            if(user.getUserRole().equals("BUSINESS_OWNER")){
                                                startActivity(new Intent(getApplicationContext(),BusinessHomeActivity.class));                                            }
                                        }
                                    }
                                }

                            }

                            @Override
                            public void onFailure(Call<LoggedInUser> call, Throwable t) {
                                Toast.makeText(getApplicationContext(),"Network Error",Toast.LENGTH_LONG);
                            }
                        });

                    }
                });

    }
}