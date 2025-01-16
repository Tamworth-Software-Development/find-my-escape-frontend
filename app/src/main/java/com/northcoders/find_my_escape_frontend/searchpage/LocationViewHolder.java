package com.northcoders.find_my_escape_frontend.searchpage;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.northcoders.find_my_escape_frontend.R;

public class LocationViewHolder extends RecyclerView.ViewHolder {

    TextView name;
    TextView description;
    public LocationViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.faveName);
        description = itemView.findViewById(R.id.faveDescription);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add logic to switch to a new page, which displays the favourite activities for this location.
                System.out.println(name.getText().toString() + " has been clicked");
            }
        });
    }
}
