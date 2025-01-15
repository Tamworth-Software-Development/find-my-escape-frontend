package com.northcoders.find_my_escape_frontend;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.northcoders.find_my_escape_frontend.databinding.ActivityNatureBinding;
import com.northcoders.find_my_escape_frontend.model.Nature;

import java.util.List;

public class NatureAdapter extends RecyclerView.Adapter<NatureAdapter.NatureViewHolder> {

    private List<Nature> natureList;

    public NatureAdapter(List<Nature> natureList) {
        this.natureList = natureList;
    }

    @NonNull
    @Override
    public NatureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ActivityNatureBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.activity_nature,
                parent,
                false
        );
        return new NatureViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NatureViewHolder holder, int position) {
        Nature nature = natureList.get(position);
        holder.binding.setNature(nature);
    }

    @Override
    public int getItemCount() {
        return natureList.size();
    }


    public static class NatureViewHolder extends RecyclerView.ViewHolder {
        private ActivityNatureBinding binding;

        public NatureViewHolder(ActivityNatureBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
