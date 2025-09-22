package com.artgallery.tracker.dto;

public class ArtDto {
    private Long id;
    private String name;
    private String image;
    private String description;
    private String username;//only exposes email(username) instead of user object

    public ArtDto(Long id, String name, String image, String description, String username) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
