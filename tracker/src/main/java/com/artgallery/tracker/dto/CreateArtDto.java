package com.artgallery.tracker.dto;

public class CreateArtDto {

    private String name;
    private String image;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CreateArtDto(String name, String image, String description) {
        this.name = name;
        this.image = image;
        this.description = description;
    }
}
