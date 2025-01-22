package com.northcoders.find_my_escape_frontend.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.northcoders.find_my_escape_frontend.BR;

public class Beach extends BaseObservable {
    private String name;
    private String restrictions;
    private String formatted;

    public Beach(String name, String restrictions, String formatted) {
        this.name = name;
        this.restrictions = restrictions;
        this.formatted = formatted;
    }

    public Beach() {
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
    public String getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
        notifyPropertyChanged(BR.restrictions);
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
