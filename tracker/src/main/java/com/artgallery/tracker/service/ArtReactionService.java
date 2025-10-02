package com.artgallery.tracker.service;

import com.artgallery.tracker.model.Art;
import com.artgallery.tracker.model.ArtReaction;
import com.artgallery.tracker.model.ReactionType;
import com.artgallery.tracker.model.User;
import com.artgallery.tracker.repository.ArtReactionRepository;
import com.artgallery.tracker.repository.ArtRepository;
import com.artgallery.tracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service

public class ArtReactionService {
    private final ArtReactionRepository reactionRepo;
    private final ArtRepository artRepo;
    private final UserRepository userRepo;

    public ArtReactionService(ArtReactionRepository reactionRepo, ArtRepository artRepo, UserRepository userRepo) {
        this.reactionRepo = reactionRepo;
        this.artRepo = artRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public void react(Long artId, String userEmail, ReactionType reactionType) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Art art = artRepo.findById(artId)
                .orElseThrow(() -> new RuntimeException("Art not found"));

        // Check if user already reacted
        Optional<ArtReaction> existing = reactionRepo.findByArt_IdAndUser_Id(artId, user.getId());

        if (existing.isPresent()) {
            ArtReaction reaction = existing.get();
            // Update if different
            if (!reaction.getReactionType().equals(reactionType)) {
                reaction.setReactionType(reactionType);
                reactionRepo.save(reaction);
            }else if (reaction.getReactionType().equals(reactionType)){
                reactionRepo.deleteById(reaction.getId());
            }
        } else {
            // Create new reaction
            ArtReaction reaction = new ArtReaction();
            reaction.setArt(art);
            reaction.setUser(user);
            reaction.setReactionType(reactionType);
            reactionRepo.save(reaction);
        }
    }

    public long countLikes(Long artId) {
        return reactionRepo.countByArt_IdAndReactionType(artId, ReactionType.LIKE);
    }

    public long countDislikes(Long artId) {
        return reactionRepo.countByArt_IdAndReactionType(artId, ReactionType.DISLIKE);
    }
}

