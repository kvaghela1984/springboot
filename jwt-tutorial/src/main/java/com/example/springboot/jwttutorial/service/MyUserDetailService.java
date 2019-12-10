package com.example.springboot.jwttutorial.service;

import com.example.springboot.jwttutorial.entity.UserCredential;
import com.example.springboot.jwttutorial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredential userCredential = usersRepository.findById(username).orElse(null);
        return new User(userCredential.getUsername(), userCredential.getPassword(),
                new ArrayList<>());
    }
}
