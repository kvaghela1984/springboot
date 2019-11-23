package com.example.springboot.user.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.user.app.models.AuthenticateRequest;
import com.example.springboot.user.app.models.SignUpRequest;
import com.example.springboot.user.app.service.LoginService;

@RestController
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity signUp(@RequestBody SignUpRequest request){
		
		loginService.signUp(request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
		
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity authenticate(@RequestBody AuthenticateRequest request){
		
		boolean authenticated = loginService.authenticate(request);
		if(authenticated) {
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		
		
	}

}
