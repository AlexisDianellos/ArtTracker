package com.artgallery.tracker.service;

import com.artgallery.tracker.dto.ArtDto;
import com.artgallery.tracker.dto.CreateArtDto;
import com.artgallery.tracker.model.Art;
import com.artgallery.tracker.model.User;
import com.artgallery.tracker.repository.ArtRepository;
import com.artgallery.tracker.repository.UserRepository;
import com.artgallery.tracker.s3.S3Service;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@Service
public class ArtService {
    private final ArtRepository artRepository;
    private final UserRepository userRepository;

    private final S3Service s3Service;

    @Value("${AWS_BUCKET_NAME}")
    private String bucketName;

    public ArtService(ArtRepository artRepository,UserRepository userRepository,S3Service s3Service) {
        this.artRepository = artRepository;
        this.userRepository=userRepository;
        this.s3Service=s3Service;
    }
    public Page<ArtDto> getAllArt(int page,int size) {
        Pageable pageable = PageRequest.of(page,size);
        return artRepository.findAll(pageable)
                .map(this::mapToDto);
    }

    @Transactional
    public ArtDto createArt(CreateArtDto request, MultipartFile imageFile, String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));

        // Upload file to S3
        String imageId= UUID.randomUUID().toString();
        String key = "art/%s/%s-%s".formatted(user.getId(), imageId, imageFile.getOriginalFilename());

        try {
            s3Service.putObject(bucketName, key, imageFile.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("S3 upload failed", e);
        }

        Art art = new Art();
        art.setName(request.getName());
        art.setDescription(request.getDescription());
        art.setImage(key);//store only the key in order to gen pre signed url to send to react
        art.setUser(user);//fk

        Art saved = artRepository.saveAndFlush(art);

        return mapToDto(saved);
    }

    private ArtDto mapToDto(Art art) {
        String presignedUrl = s3Service.generatePresignedUrl(bucketName, art.getImage(), 10);
        return new ArtDto(
                art.getId(),
                art.getName(),
                art.getImage(),
                art.getDescription(),
                art.getUser().getDisplayName(),
                presignedUrl
        );
    }
}
