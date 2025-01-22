package com.northcoders.find_my_escape_frontend;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.northcoders.find_my_escape_frontend.databinding.ActivitySportBinding;
import com.northcoders.find_my_escape_frontend.model.Sport;

import java.util.List;

public class SportAdapter extends RecyclerView.Adapter<SportAdapter.SportViewHolder> {

    private List<Sport> sportList;

    public SportAdapter(List<Sport> sportList) {
        this.sportList = sportList;
    }

    @NonNull
    @Override
    public SportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ActivitySportBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.activity_sport,
                parent,
                false
        );
        return new SportViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SportViewHolder holder, int position) {
        Sport sport = sportList.get(position);
        holder.binding.setSport(sport);
    }

    @Override
    public int getItemCount() {
        if (sportList != null) {
            return sportList.size();
        }
        return 0;
    }


    public static class SportViewHolder extends RecyclerView.ViewHolder {
        private ActivitySportBinding binding;

        public SportViewHolder(ActivitySportBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
