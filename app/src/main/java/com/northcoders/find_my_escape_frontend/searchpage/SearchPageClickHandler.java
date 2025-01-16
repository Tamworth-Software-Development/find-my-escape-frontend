package com.northcoders.find_my_escape_frontend.searchpage;

import com.northcoders.find_my_escape_frontend.model.Location;

import java.util.List;

public class SearchPageClickHandler {
    public void goButtonClicked(List<String> locations, List<String> placeIds, String s){
        for (int i = 0; i < locations.size(); i++){
            if (locations.get(i).equals(s)){
                String formatted = locations.get(i);
                String place_id = placeIds.get(i);
                // call update method i.e updateUser(int userId, String formatted, String place_id)
                // Change the view using intent to the destination view.
            }
        }
    }

    public void recyclerViewClicked(){
        //Add logic to switch to the view for the specific location showing the favourite activities.
    }


}
