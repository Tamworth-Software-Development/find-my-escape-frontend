package com.northcoders.find_my_escape_frontend.model;

public class Location {
    private String name;
    private String description;
    private String image;

    public Location() {}

    public Location(String name, String description, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
