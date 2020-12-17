package com.example.weddingelements_android.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.weddingelements_android.R;
import com.example.weddingelements_android.interfaces.RestApi;
import com.example.weddingelements_android.model.BusinessOwner;
import com.example.weddingelements_android.util.Validator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BusinessOwnerRegistration extends AppCompatActivity {
    Spinner category,country;
    String countryString,categoryString;
    EditText name,description,email,contact,passoword1,password2,address;
    AppCompatButton regBtn;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_owner_registration);
        regBtn = findViewById(R.id.regb_regBtn);
        name = findViewById(R.id.regb_name);
        email = findViewById(R.id.regb_email);
        contact = findViewById(R.id.regb_contact);
        address = findViewById(R.id.regb_address);
        description = findViewById(R.id.regb_description);
        passoword1 = findViewById(R.id.regb_password);
        password2 = findViewById(R.id.regb_password2);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Validator.passwordValidator(passoword1.getText().toString(),password2.getText().toString())){
                    System.out.println("Passwords MAtch");
                    BusinessOwner businessOwner = new BusinessOwner();
                    businessOwner.setBusinessType(categoryString);
                    businessOwner.setCountry(countryString);
                    businessOwner.setName(name.getText().toString());
                    businessOwner.setEmail(email.getText().toString());
                    businessOwner.setContactNo(contact.getText().toString());
                    businessOwner.setDescription(description.getText().toString());
                    businessOwner.setAddress(address.getText().toString());
                    businessOwner.setPassword(passoword1.getText().toString());
                    registerBusiness(businessOwner);


                }
                else{
                    System.out.println("PW Do Not Match");
                    Toast.makeText(getApplicationContext(),"The Passwords do not Match",Toast.LENGTH_LONG);
                }
            }
        });

        category = (Spinner) findViewById(R.id.category_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);
        country = (Spinner) findViewById(R.id.country_spinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.country, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(adapter1);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryString = category.getItemAtPosition(position).toString();
                System.out.println(categoryString);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                countryString = country.getItemAtPosition(position).toString();
                System.out.println(countryString);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void registerBusiness(BusinessOwner businessOwner) {
        retrofit = new Retrofit.Builder().baseUrl(RestApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RestApi api = retrofit.create(RestApi.class);
        Call<BusinessOwner> call = api.registerBusiness(businessOwner);
        call.enqueue(new Callback<BusinessOwner>() {
            @Override
            public void onResponse(Call<BusinessOwner> call, Response<BusinessOwner> response) {
                System.out.println("Success");
                System.out.println(response.body().getName());
            }

            @Override
            public void onFailure(Call<BusinessOwner> call, Throwable t) {
                System.out.println("Fail");

            }
        });

    }
}