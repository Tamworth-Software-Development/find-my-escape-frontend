package com.northcoders.find_my_escape_frontend;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.northcoders.find_my_escape_frontend.databinding.ActivityMuseumBinding;
import com.northcoders.find_my_escape_frontend.model.Museum;

import java.util.List;

public class MuseumAdapter extends RecyclerView.Adapter<MuseumAdapter.MuseumViewHolder>{

    private List<Museum> museums;

    public MuseumAdapter(List<Museum> museums) {
        this.museums = museums;
    }

    @NonNull
    @Override
    public MuseumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ActivityMuseumBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.activity_museum,
                parent,
                false
        );
        return new MuseumViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MuseumViewHolder holder, int position) {
        Museum museum = museums.get(position);
        holder.binding.setMuseum(museum);
    }

    @Override
    public int getItemCount() {
        return museums.size();
    }

    public static class MuseumViewHolder extends RecyclerView.ViewHolder {
        private ActivityMuseumBinding binding;

        public MuseumViewHolder(ActivityMuseumBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
