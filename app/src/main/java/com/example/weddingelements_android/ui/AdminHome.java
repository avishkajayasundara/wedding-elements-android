package com.example.weddingelements_android.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.weddingelements_android.R;

public class AdminHome extends AppCompatActivity {

    AppCompatButton customers,business,advertisements,inquiries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        customers = findViewById(R.id.admin_cus);
        business = findViewById(R.id.admin_bus);
        advertisements = findViewById(R.id.admin_adv);
        inquiries = findViewById(R.id.admin_inq);

        customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdminManageCustomers.class));
            }
        });
        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdminManageBusiness.class));

            }
        });
        advertisements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdminManageAdvertisements.class));
            }
        });
        inquiries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdminManageInquiries.class));
            }
        });

    }
}