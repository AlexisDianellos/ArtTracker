package com.artgallery.tracker.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="art")
public class Art {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String image;
    @Column(nullable = false)
    private String description;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // foreign key column in "art" table
    private User user;

    public Art(){}

    public Art(String name, String image, String description) {
        this.name = name;
        this.image = image;
        this.description = description;
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

    public User getUser() {
        return user;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
