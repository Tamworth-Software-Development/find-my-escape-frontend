package com.northcoders.find_my_escape_frontend.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.google.gson.annotations.SerializedName;
import com.northcoders.find_my_escape_frontend.BR;

public class Nature extends BaseObservable {
    private String name;
    @SerializedName("opening_hours")
    private String openingHours;
    private String formatted;

    public Nature(String name, String openingHours, String formatted) {
        this.name = name;
        this.openingHours = openingHours;
        this.formatted = formatted;
    }

    public Nature() {
    }
    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
    @Bindable
    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
        notifyPropertyChanged(BR.openingHours);
    }
    @Bindable
    public String getFormatted() {
        return formatted;
    }

    public void setFormatted(String formatted) {
        this.formatted = formatted;
        notifyPropertyChanged(BR.formatted);
    }
}
