package com.artgallery.tracker.repository;

import com.artgallery.tracker.model.Art;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArtRepository extends JpaRepository<Art, Long> {
    Optional<Art> findById(Long id);

    //returns all art a user has liked
    @Query("""
        SELECT r.art
        FROM ArtReaction r
        WHERE r.user.id = :userId AND r.reactionType = 'LIKE'
    """)
    List<Art> findLikedByUser(Long userId);

    // Returns all art the user has NOT reacted to yet (unseen candidates)
    @Query("""
        SELECT a
        FROM Art a
        WHERE a.id NOT IN (
            SELECT r.art.id FROM ArtReaction r
            WHERE r.user.id = :userId
        )
    """)
    Page<Art> findUnseenArt(Long userId, Pageable pageable);


}
