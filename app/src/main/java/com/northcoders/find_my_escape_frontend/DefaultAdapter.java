package com.northcoders.find_my_escape_frontend;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.northcoders.find_my_escape_frontend.databinding.ActivityDefaultBinding;
import com.northcoders.find_my_escape_frontend.model.Default;

import java.util.List;

public class DefaultAdapter extends RecyclerView.Adapter<DefaultAdapter.DefaultViewHolder> {

    private List<Default> defaultActivities;

    public DefaultAdapter(List<Default> defaultActivities) {
        this.defaultActivities = defaultActivities;
    }

    @NonNull
    @Override
    public DefaultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ActivityDefaultBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.activity_default,
                parent,
                false
        );
        return new DefaultViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultViewHolder holder, int position) {
        Default defaultActivity = defaultActivities.get(position);
        holder.binding.setDefaultActivity(defaultActivity);
    }

    @Override
    public int getItemCount() {
        if (defaultActivities != null) {
            return defaultActivities.size();
        }
        return 0;
    }

    public static class DefaultViewHolder extends RecyclerView.ViewHolder {
        private ActivityDefaultBinding binding;

        public DefaultViewHolder(ActivityDefaultBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
