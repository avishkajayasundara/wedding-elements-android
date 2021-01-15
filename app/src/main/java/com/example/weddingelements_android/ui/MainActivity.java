package com.example.weddingelements_android.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.weddingelements_android.R;
import com.example.weddingelements_android.model.Cache;
import com.example.weddingelements_android.model.LoggedInUser;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    Button regBtn;
    Button lgnBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Paper.init(getApplicationContext());
        LoggedInUser user = Paper.book().read("User");
        if(user != null){
            Cache.user = user;
            if(user.getUserRole().equals("ADMIN")){
                startActivity(new Intent(getApplicationContext(),AdminHome.class));
            }else{
                if(user.getUserRole().equals("CUSTOMER"))
                    startActivity(new Intent(getApplicationContext(),Home.class));
                else
                    startActivity(new Intent(getApplicationContext(),BusinessHomeActivity.class));
            }

        }

        regBtn = (Button)findViewById(R.id.main_register);
        lgnBtn = (Button)findViewById(R.id.main_login);
        lgnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence options[]=new CharSequence[]
                        {
                                "Customer",
                                "Business Owner"
                        };
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Select Account Type");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){
                            startActivity(new Intent(MainActivity.this,CustomerRegistrationActivity.class));
                        }else{
                            startActivity(new Intent(getApplicationContext(),BusinessOwnerRegistration.class));
                        }
                    }
                });
                builder.show();
            }
        });
    }
}