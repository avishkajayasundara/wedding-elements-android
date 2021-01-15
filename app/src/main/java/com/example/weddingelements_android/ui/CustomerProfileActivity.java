package com.example.weddingelements_android.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weddingelements_android.R;
import com.example.weddingelements_android.interfaces.RestApi;
import com.example.weddingelements_android.model.Cache;
import com.example.weddingelements_android.model.Customer;
import com.example.weddingelements_android.model.CustomerUpdateRequest;


import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomerProfileActivity extends AppCompatActivity {

    Retrofit retrofit;
    Customer customer;
    EditText name, address, contactNo, dob, email, password;
    TextView nameTop;
    AppCompatButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);
        name = findViewById(R.id.prof_name);
        address = findViewById(R.id.prof_address);
        contactNo = findViewById(R.id.prof_contact);
        dob = findViewById(R.id.prof_dob);
        email = findViewById(R.id.prof_email);
        nameTop = findViewById(R.id.prof_name_top);
        password = findViewById(R.id.prof_password);
        retrofit = new Retrofit.Builder().baseUrl(RestApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestApi api = retrofit.create(RestApi.class);
        button = findViewById(R.id.prof_update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerUpdateRequest request = new CustomerUpdateRequest(
                        contactNo.getText().toString(), address.getText().toString(), password.getText().toString(),
                        email.getText().toString()
                );
                retrofit2.Call<Void> call = api.updateCustomer(request);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        System.out.println("Updated Successfully");
                        Toast.makeText(getApplicationContext(),"Details Updated Successfully", Toast.LENGTH_LONG);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        System.out.println("Failed ");

                    }
                });

            }
        });
        retrofit2.Call<Customer> call = api.getCustomerDetails(Cache.user.getUsername());
        call.enqueue(new retrofit2.Callback<Customer>() {
            @Override
            public void onResponse(retrofit2.Call<Customer> call, retrofit2.Response<Customer> response) {
                customer = response.body();
                name.setText(customer.getFirstName() + " " + customer.getLastName());
                email.setText(customer.getEmail());
                dob.setText(customer.getDob());
                address.setText(customer.getAddress());
                contactNo.setText(customer.getContactNo());
                password.setText(customer.getPassword());
            }

            @Override
            public void onFailure(retrofit2.Call<Customer> call, Throwable t) {
                System.out.println("Failure");

            }
        });

    }
}