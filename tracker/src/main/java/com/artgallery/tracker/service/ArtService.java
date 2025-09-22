package com.artgallery.tracker.service;

import com.artgallery.tracker.dto.ArtDto;
import com.artgallery.tracker.dto.CreateArtDto;
import com.artgallery.tracker.model.Art;
import com.artgallery.tracker.model.User;
import com.artgallery.tracker.repository.ArtRepository;
import com.artgallery.tracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ArtService {
    private final ArtRepository artRepository;
    private final UserRepository userRepository;

    public ArtService(ArtRepository artRepository,UserRepository userRepository) {
        this.artRepository = artRepository;
        this.userRepository=userRepository;
    }
    public Page<ArtDto> getAllArt(int page,int size) {
        Pageable pageable = PageRequest.of(page,size);
        return artRepository.findAll(pageable)
                .map(this::mapToDto);
    }

    @Transactional
    public ArtDto createArt(CreateArtDto request, String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));
        Art art = new Art();
        art.setName(request.getName());
        art.setDescription(request.getDescription());
        art.setImage(request.getImage());
        art.setUser(user);//fk

        Art saved = artRepository.saveAndFlush(art);

        return mapToDto(saved);
    }

    private ArtDto mapToDto(Art art) {
        return new ArtDto(
                art.getId(),
                art.getName(),
                art.getImage(),
                art.getDescription(),
                art.getUser().getDisplayName()
        );
    }
}
