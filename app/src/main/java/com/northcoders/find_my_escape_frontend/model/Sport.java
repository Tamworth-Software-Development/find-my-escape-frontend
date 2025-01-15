package com.northcoders.find_my_escape_frontend.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.northcoders.find_my_escape_frontend.BR;

public class Sport extends BaseObservable {
    private String name;
    private String leisure;
    private String openingHours;
    private String formatted;
    private String website;

    public Sport(String name, String leisure, String openingHours, String formatted, String website) {
        this.name = name;
        this.leisure = leisure;
        this.openingHours = openingHours;
        this.formatted = formatted;
        this.website = website;
    }

    public Sport() {
    }
    @Bindable
    public String getLeisure() {
        return leisure;
    }

    public void setLeisure(String leisure) {
        this.leisure = leisure;
        notifyPropertyChanged(BR.leisure);
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
    @Bindable
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
        notifyPropertyChanged(BR.website);
    }
}
