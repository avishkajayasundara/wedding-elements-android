package com.example.weddingelements_android.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weddingelements_android.R;
import com.example.weddingelements_android.interfaces.RestApi;
import com.example.weddingelements_android.model.Review;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    List<Review> reviewList;
    boolean inAdv;
    Retrofit retrofit;

    public ReviewAdapter(List<Review> reviewList, boolean inAdv) {
        this.reviewList = reviewList;
        this.inAdv = inAdv;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_layout, parent, false);
        return new ReviewAdapter.ViewHolder(view);
    }

    private void deleteReview(int position,View v) {
        retrofit = new Retrofit.Builder().baseUrl(RestApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestApi api = retrofit.create(RestApi.class);
        Call<List<Review>> call = api.deleteReview(reviewList.get(position).getReviewId());
        call.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                System.out.println("Review was Deleted");
                Toast.makeText(v.getContext(),"The Review was Deleted",Toast.LENGTH_LONG);
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                System.out.println("Failed");

            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getRatingBar().setRating(reviewList.get(position).getScore()/2);
        holder.getRating().setText(String.valueOf(reviewList.get(position).getScore())+"/10");
        holder.getCustomer().setText(reviewList.get(position).getName());
        holder.getDescription().setText(reviewList.get(position).getReview());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inAdv==false){
                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                            alert.setTitle("Do you want to delete this review?");
                            alert.setMessage("Your Review will be Permanantly Deleted");

                            alert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    deleteReview(position,v);
                                    reviewList.remove(position);
                                    notifyDataSetChanged();
                                }
                            });

                            alert.setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {

                                        }
                                    });

                            alert.show();
                        }
                    });
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private RatingBar ratingBar;
        private TextView rating;
        private EditText description;
        private TextView customer;

        public ViewHolder(@NonNull View v) {
            super(v);
            ratingBar = v.findViewById(R.id.rev_ratingbar);
            rating = v.findViewById(R.id.rev_score);
            description = v.findViewById(R.id.rev_des);
            customer = v.findViewById(R.id.rev_cus);
        }

        public RatingBar getRatingBar() {
            return ratingBar;
        }

        public void setRatingBar(RatingBar ratingBar) {
            this.ratingBar = ratingBar;
        }

        public TextView getRating() {
            return rating;
        }

        public void setRating(TextView rating) {
            this.rating = rating;
        }

        public EditText getDescription() {
            return description;
        }

        public void setDescription(EditText description) {
            this.description = description;
        }

        public TextView getCustomer() {
            return customer;
        }

        public void setCustomer(TextView customer) {
            this.customer = customer;
        }
    }

}
