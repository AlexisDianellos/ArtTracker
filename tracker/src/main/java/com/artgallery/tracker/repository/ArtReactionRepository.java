package com.artgallery.tracker.repository;

import com.artgallery.tracker.model.ArtReaction;
import com.artgallery.tracker.model.ReactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtReactionRepository extends JpaRepository<ArtReaction, Long> {
    long countByArt_IdAndReactionType(Long artId, ReactionType reactionType);
    Optional<ArtReaction> findByArt_IdAndUser_Id(Long artId, Long userId);

    void deleteById(Long id);
}
