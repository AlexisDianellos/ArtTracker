package com.artgallery.tracker.model;

import jakarta.persistence.*;
@Entity
@Table(name="art_reaction")
public class ArtReaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "reaction_type",nullable = false)
    private ReactionType reactionType; // LIKE or DISLIKE

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "art_id", nullable = false) // fk column for art
    private Art art;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // fk column for user
    private User user;

    public ArtReaction( Art art, User user,ReactionType reactionType) {
        this.art = art;
        this.user = user;
        this.reactionType=reactionType;
    }
    public ArtReaction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Art getArt() {
        return art;
    }

    public void setArt(Art art) {
        this.art = art;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ReactionType getReactionType() {
        return reactionType;
    }

    public void setReactionType(ReactionType reactionType) {
        this.reactionType = reactionType;
    }
}
