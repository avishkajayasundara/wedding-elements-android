package com.example.weddingelements_android.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.weddingelements_android.R;
import com.example.weddingelements_android.interfaces.RestApi;
import com.example.weddingelements_android.model.Customer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CustomerRegistrationActivity extends AppCompatActivity {
    private EditText fname, lname, email, contactNo, dob, password1, password2, address;
    private RadioButton male,female,other;
    private AppCompatButton button;
    String _gender;
    Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);
        fname = findViewById(R.id.reg_fname);
        lname = findViewById(R.id.reg_lname);
        address = findViewById(R.id.reg_address);
        contactNo = findViewById(R.id.reg_contact);
        dob = findViewById(R.id.reg_dob);
        email = findViewById(R.id.reg_email);
        password1 = findViewById(R.id.reg_password);
        password2 = findViewById(R.id.reg_password2);
        male = findViewById(R.id.gen_male);
        female = findViewById(R.id.gen_female);
        other = findViewById(R.id.gen_other);
        button = findViewById(R.id.reg_regBtn);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                female.setChecked(false);
                other.setChecked(false);
                _gender = "Male";
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                male.setChecked(false);
                other.setChecked(false);
                _gender = "Female";
            }
        });
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                male.setChecked(false);
                female.setChecked(false);
                _gender = "Other";
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Customer customer = new Customer();
                    customer.setFirstName(fname.getText().toString());
                    customer.setLastName(lname.getText().toString());
                    customer.setDob(dob.getText().toString());
                    customer.setAddress(address.getText().toString());
                    customer.setContactNo(contactNo.getText().toString());
                    customer.setPassword(password1.getText().toString());
                    customer.setStatus("Active");
                    customer.setUserRole("CUSTOMER");
                    customer.setEmail(email.getText().toString());
                    customer.setGender(_gender);
                    sendRequest(customer,v);

            }
        });
    }

    private void sendRequest(Customer customer, View v) {

        retrofit = new Retrofit.Builder().baseUrl(RestApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RestApi api = retrofit.create(RestApi.class);
        Call<String> call = api.registerCustomer(customer);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                System.out.println("Success");
                System.out.println(response.body());
                System.out.println("Registered Successfully");
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                System.out.println("fail");
            }
        });
    }
}