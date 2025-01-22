package com.northcoders.find_my_escape_frontend;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.northcoders.find_my_escape_frontend.databinding.ActivityRestaurantBinding;
import com.northcoders.find_my_escape_frontend.model.Restaurant;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>{

    private List<Restaurant> restaurants;

    public RestaurantAdapter(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ActivityRestaurantBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.activity_restaurant,
                parent,
                false
        );
        return new RestaurantViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = restaurants.get(position);
        holder.binding.setRestaurant(restaurant);
    }

    @Override
    public int getItemCount() {
        if (restaurants != null) {
            return restaurants.size();
        }
        return 0;
    }


    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private ActivityRestaurantBinding binding;

        public RestaurantViewHolder(ActivityRestaurantBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
