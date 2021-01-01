package com.example.weddingelements_android.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weddingelements_android.R;
import com.example.weddingelements_android.interfaces.RestApi;
import com.example.weddingelements_android.model.Advertisement;
import com.example.weddingelements_android.model.BusinessOwner;
import com.example.weddingelements_android.model.Customer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminCustomerAdapter extends RecyclerView.Adapter<AdminCustomerAdapter.ViewHolder> {
    private List<BusinessOwner> businessOwners;
    private boolean isCustomer;
    private List<Customer> customers;
    Retrofit retrofit;
    RestApi api;

    public AdminCustomerAdapter(List<Customer> customers) {
        this.customers = customers;
        isCustomer = true;
        retrofit = new Retrofit.Builder().baseUrl(RestApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(RestApi.class);

    }

    public AdminCustomerAdapter(List<BusinessOwner> businessOwners, boolean isCustomer) {
        this.businessOwners = businessOwners;
        this.isCustomer = isCustomer;
        retrofit = new Retrofit.Builder().baseUrl(RestApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(RestApi.class);
    }

    public AdminCustomerAdapter() {
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_customer_layout, parent, false);

        return new AdminCustomerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(isCustomer==true){
            holder.name.setText("Name : "+customers.get(position).getFirstName()+" "+customers.get(position).getLastName());
            holder.email.setText("Email : "+customers.get(position).getEmail());
            holder.contact.setText("Contact Number : "+customers.get(position).getContactNo());
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Call<Void> call = api.deleteCustomer(customers.get(position).getEmail());
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            System.out.println("Deleted");
                            customers.remove(position);
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            System.out.println("Failed");
                        }
                    });
                }
            });
        } else{
            holder.name.setText("Name : "+businessOwners.get(position).getName());
            holder.email.setText("Email : "+businessOwners.get(position).getEmail());
            holder.contact.setText("Category : "+businessOwners.get(position).getContactNo());
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Call<Void> call = api.deleteBusiness(businessOwners.get(position).getEmail());
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            System.out.println("Success");
                            businessOwners.remove(position);
                            notifyDataSetChanged();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            System.out.println("Fail");
                        }
                    });

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if(isCustomer==true){
            return customers.size();
        }else{
            return businessOwners.size();
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,email,contact;
        AppCompatButton button;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.cus_name);
            email = view.findViewById(R.id.cus_email);
            contact = view.findViewById(R.id.cus_contact);
            button = view.findViewById(R.id.cus_btn);

        }
    }
}
