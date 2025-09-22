package com.artgallery.tracker.repository;

import com.artgallery.tracker.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {//user w prim key Long(id)
    Optional<User> findByEmail(String email);//not guaranteed search returns user: finding user by email
    Optional<User> findByVerificationCode(String verificationCode);//not guaranteed search returns user: finding user by vercode
    Optional<User> findByUsername(String username);
}
