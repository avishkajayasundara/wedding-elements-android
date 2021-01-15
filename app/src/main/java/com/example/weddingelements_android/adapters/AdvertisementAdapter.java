package com.example.weddingelements_android.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weddingelements_android.R;
import com.example.weddingelements_android.model.Advertisement;
import com.example.weddingelements_android.ui.SIngleAdvertisementActivity;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;


public class AdvertisementAdapter extends RecyclerView.Adapter<AdvertisementAdapter.ViewHolder> {


    private List<Advertisement> advertisements;

    public AdvertisementAdapter(List<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }

    public AdvertisementAdapter() {
    }

    public List<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(List<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_items_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        float rating = 0;
//        viewHolder.getTextView().setText(localDataSet[position]);
        holder.getTitle().setText(advertisements.get(position).getTitle());
        holder.getStartingPrice().setText(advertisements.get(position).getStartingPrice().toString());
        Picasso.get().load(advertisements.get(position).getImage()).into(holder.getImage());
        if(advertisements.get(position).getNumberOfReviews()==0){
            rating = 0;
            holder.getRatingBar().setRating(rating);

        }else{
            rating = advertisements.get(position).getScore() / advertisements.get(position).getNumberOfReviews();
            holder.getRatingBar().setRating(rating / 2);
        }
        holder.getRating().setText(String.valueOf(rating));
        holder.getViewButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                Intent intent = new Intent(v.getContext(), SIngleAdvertisementActivity.class);
                intent.putExtra("Advertisement", gson.toJson(advertisements.get(position)));
                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return advertisements.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView startingPrice;
        private ImageView image;
        private Button viewButton;
        private RatingBar ratingBar;
        private TextView rating;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.adv_title);
            startingPrice = view.findViewById(R.id.adv_price);
            image = view.findViewById(R.id.adv_image);
            viewButton = view.findViewById(R.id.adv_viewmore);
            ratingBar = view.findViewById(R.id.adv_ratingbar);
            rating = view.findViewById(R.id.adv_rating);
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

        public TextView getTitle() {
            return title;
        }

        public void setTitle(TextView title) {
            this.title = title;
        }


        public ImageView getImage() {
            return image;
        }

        public void setImage(ImageView image) {
            this.image = image;
        }

        public Button getViewButton() {
            return viewButton;
        }

        public void setViewButton(Button viewButton) {
            this.viewButton = viewButton;
        }

        public TextView getStartingPrice() {
            return startingPrice;
        }

        public void setStartingPrice(TextView startingPrice) {
            this.startingPrice = startingPrice;
        }
    }

}
