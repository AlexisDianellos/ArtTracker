package com.artgallery.tracker.dto;

public class CreateArtDto {

    private String name;
    private String description;

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

    public CreateArtDto(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
