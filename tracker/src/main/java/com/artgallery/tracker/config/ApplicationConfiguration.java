package com.artgallery.tracker.config;

import com.artgallery.tracker.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationConfiguration {//check if user is real when they login
    private final UserRepository userRepository;
    public ApplicationConfiguration(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    @Bean
    UserDetailsService userDetailsService(){//c if user w email exists
        return email -> userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
    @Bean
    BCryptPasswordEncoder passwordEncoder(){//hash to compare to stored hash version
        return new BCryptPasswordEncoder();
    }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config)throws Exception{
        return config.getAuthenticationManager();//runs checks through providers for user who tries to login
    }
    @Bean
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());//user
        authProvider.setPasswordEncoder(passwordEncoder());//comp passwords
        return authProvider;
    }

}
