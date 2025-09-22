package com.artgallery.tracker.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="users")
public class User implements UserDetails {//UserDetails integrates w spring sec for details
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique=true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private boolean enabled;
    @Column(name="verification_code")
    private String verificationCode;
    @Column(name="verification_expiration")
    private LocalDateTime verificationCodeExpiresAt;

    //constructor for creating an unverified user
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    //default constructor
    public User() {
    }
    //implement methods for user details
    @Override
    public Collection<?extends GrantedAuthority> getAuthorities(){
        return List.of();//users role list , empty bc no role auth
    }
    @Override
    public boolean isAccountNonExpired(){
        return true;
    }
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }
    @Override
    public boolean isEnabled(){
        return enabled;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getDisplayName(){
        return username;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public LocalDateTime getVerificationCodeExpiresAt() {
        return verificationCodeExpiresAt;
    }

    public void setVerificationCodeExpiresAt(LocalDateTime verificationCodeExpiresAt) {
        this.verificationCodeExpiresAt = verificationCodeExpiresAt;
    }
}
