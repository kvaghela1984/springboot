package com.example.springboot.user.app.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.user.app.models.AuthenticateRequest;
import com.example.springboot.user.app.models.ProfileUpdate;
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
	public ResponseEntity authenticate(HttpServletRequest httpServletRequest, @RequestBody AuthenticateRequest request){
		
		boolean authenticated = loginService.authenticate(request);
		if(authenticated) {
			HttpSession session = httpServletRequest.getSession(true);
			
			session.setMaxInactiveInterval(180);
			session.setAttribute("user", request.getUserName());
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
	}
	
	@RequestMapping(value = "/update-profile", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> updateProfile(HttpServletRequest httpServletRequest, @RequestBody ProfileUpdate request){
		
		HttpSession session = httpServletRequest.getSession(false);
		
		
		if((session == null) | (session != null && session.getAttribute("user") == null)){
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Login To update your profile");
		}
		httpServletRequest.changeSessionId();
		return ResponseEntity.status(HttpStatus.OK).body("Profile updated");

	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> logout(HttpServletRequest httpServletRequest){
		
		HttpSession session = httpServletRequest.getSession(false);
		
		if(session != null){
			session.invalidate();
		}

		return ResponseEntity.status(HttpStatus.OK).body("Successfully logged out");

	}

}
