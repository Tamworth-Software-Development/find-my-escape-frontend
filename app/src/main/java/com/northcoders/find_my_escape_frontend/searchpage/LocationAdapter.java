package com.northcoders.find_my_escape_frontend.searchpage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.northcoders.find_my_escape_frontend.R;
import com.northcoders.find_my_escape_frontend.databinding.LocationViewBinding;
import com.northcoders.find_my_escape_frontend.model.Location;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
    List<Location> locations;
    Context context;

    public LocationAdapter(List<Location> locations, Context context) {
        this.locations = locations;
        this.context = context;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LocationViewBinding locationViewBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.location_view,
                parent,
                false
        );
        return new LocationViewHolder(locationViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Location location = locations.get(position);
        holder.binding.setLocation(location);
    }


    @Override
    public int getItemCount() {
        return locations.size();
    }

    public static class LocationViewHolder extends RecyclerView.ViewHolder{
        private LocationViewBinding binding;
        public LocationViewHolder(LocationViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
