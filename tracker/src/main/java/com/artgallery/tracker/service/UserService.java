package com.artgallery.tracker.service;

import com.artgallery.tracker.model.User;
import com.artgallery.tracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository, EmailService emailService){
        this.userRepository=userRepository;
    }
    public List<User> allUsers(){
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
}
