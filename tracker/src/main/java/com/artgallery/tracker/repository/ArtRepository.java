package com.artgallery.tracker.repository;

import com.artgallery.tracker.model.Art;
import com.artgallery.tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtRepository extends JpaRepository<Art, Long> {
}
