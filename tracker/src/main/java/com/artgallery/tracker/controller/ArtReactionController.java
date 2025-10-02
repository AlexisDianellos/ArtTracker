package com.artgallery.tracker.controller;

import com.artgallery.tracker.model.ArtReaction;
import com.artgallery.tracker.model.ReactionType;
import com.artgallery.tracker.service.ArtReactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/art/{artId}/reactions")
@RestController
public class ArtReactionController {
    private final ArtReactionService reactionService;

    public ArtReactionController(ArtReactionService reactionService) {
        this.reactionService = reactionService;
    }

    @PostMapping("/like")
    public ResponseEntity<?> like(
            @PathVariable Long artId,
            @AuthenticationPrincipal UserDetails userDetails) {
        reactionService.react(artId, userDetails.getUsername(), ReactionType.LIKE);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/dislike")
    public ResponseEntity<?> dislike(
            @PathVariable Long artId,
            @AuthenticationPrincipal UserDetails userDetails) {
        reactionService.react(artId, userDetails.getUsername(), ReactionType.DISLIKE);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/counts")
    public ResponseEntity<Map<String, Long>> counts(@PathVariable Long artId) {
        return ResponseEntity.ok(Map.of(
                "likes", reactionService.countLikes(artId),
                "dislikes", reactionService.countDislikes(artId)
        ));
    }
}
