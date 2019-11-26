package com.example.springboot.user.app.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springboot.user.app.entity.User;
import com.example.springboot.user.app.models.AuthenticateRequest;
import com.example.springboot.user.app.models.SignUpRequest;
import com.example.springboot.user.app.repository.UsersRepository;

@Service
public class LoginService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UsersRepository usersRepository;

	public void signUp(SignUpRequest signUpRequest) {

		User entity = new User(signUpRequest.getUserName(), signUpRequest.getEmail(),
				passwordEncoder.encode(signUpRequest.getPassWord()), new Timestamp(Instant.now().toEpochMilli()),
				new Timestamp(Instant.now().toEpochMilli()));
		usersRepository.save(entity);

	}

	public boolean authenticate(AuthenticateRequest authenticateRequest) {
		
		User user = usersRepository.findById(authenticateRequest.getUserName()).orElse(null);
		if (user != null) {
			return passwordEncoder.matches(authenticateRequest.getPassWord(), user.getPassword());

		}

		return false;

	}

}
