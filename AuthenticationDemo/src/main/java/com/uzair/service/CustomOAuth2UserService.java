package com.uzair.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.uzair.entity.UserEntity;
import com.uzair.repository.UserRepository;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
		
    @Autowired
    private UserRepository userRepository;

    public OAuth2User loadUser(OAuth2User oAuth2User) throws OAuth2AuthenticationException {

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");   

        
        UserEntity user = userRepository.findByEmail(email);                             // Check if user already exists
        
        if (user == null) {
            
            user = new UserEntity();                                                 // Create a new user and save to the database without a password
            user.setEmail(email);
            user.setName(name);            
            userRepository.save(user);
        }

        
        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());        // Add the user's ID to the attributes map
        attributes.put("id", user.getId());
                            

        return new DefaultOAuth2User(
            Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
            attributes,
            "email");                                                           // Assuming "email" is the key for the user's email in the attributes
    }
}

