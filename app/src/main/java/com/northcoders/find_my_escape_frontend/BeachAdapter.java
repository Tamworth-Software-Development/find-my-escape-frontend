package com.northcoders.find_my_escape_frontend;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.northcoders.find_my_escape_frontend.databinding.ActivityBeachBinding;
import com.northcoders.find_my_escape_frontend.model.Beach;

import java.util.List;

public class BeachAdapter extends RecyclerView.Adapter<BeachAdapter.BeachViewHolder> {

    private List<Beach> beaches;

    public BeachAdapter(List<Beach> beaches) {
        this.beaches = beaches;
    }

    @NonNull
    @Override
    public BeachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ActivityBeachBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.activity_beach,
                parent,
                false
        );
        return new BeachViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BeachViewHolder holder, int position) {
        Beach beach = beaches.get(position);
        holder.binding.setBeach(beach);
    }

    @Override
    public int getItemCount() {
        return beaches.size();
    }

    public static class BeachViewHolder extends RecyclerView.ViewHolder {
        private ActivityBeachBinding binding;

        public BeachViewHolder(ActivityBeachBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
