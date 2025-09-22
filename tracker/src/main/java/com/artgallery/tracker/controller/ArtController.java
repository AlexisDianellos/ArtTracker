package com.artgallery.tracker.controller;

import com.artgallery.tracker.dto.ArtDto;
import com.artgallery.tracker.dto.CreateArtDto;
import com.artgallery.tracker.dto.RegisterUserDto;
import com.artgallery.tracker.model.Art;
import com.artgallery.tracker.model.User;
import com.artgallery.tracker.service.ArtService;
import com.artgallery.tracker.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/art")
@RestController
public class ArtController {

    private final ArtService artService;
    private final UserService userService;

    public ArtController(ArtService artService, UserService userService) {
        this.artService = artService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<ArtDto> createArt(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CreateArtDto createArtDto){
        ArtDto createdArt=artService.createArt(createArtDto,userDetails.getUsername());//jwt sub is set to email so get username act returns email
        return ResponseEntity.ok(createdArt);
    }
    @GetMapping("/")
    public ResponseEntity<Page<ArtDto>> allArt(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        Page<ArtDto> allArt=artService.getAllArt(page,size);
        return ResponseEntity.ok(allArt);
    }
}
