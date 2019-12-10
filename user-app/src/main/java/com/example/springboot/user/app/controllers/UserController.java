package com.example.springboot.user.app.controllers;

import com.example.springboot.user.app.entity.UserProfile;
import com.example.springboot.user.app.models.AuthenticateRequest;
import com.example.springboot.user.app.models.ErrorResponse;
import com.example.springboot.user.app.models.ProfileUpdate;
import com.example.springboot.user.app.models.SignUpRequest;
import com.example.springboot.user.app.service.LoginService;
import com.example.springboot.user.app.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class UserController {
	
	@Autowired
	private LoginService loginService;

	@Autowired
	private ProfileService profileService;
	
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
	public ResponseEntity updateProfile(HttpServletRequest httpServletRequest, @RequestBody ProfileUpdate request){
		
		HttpSession session = httpServletRequest.getSession(false);

		if((session == null) | (session != null && session.getAttribute("user") == null)){
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("Login To update your profile"));
		}
		profileService.updateProfile(request, (String) session.getAttribute("user"));

		httpServletRequest.changeSessionId();
		return ResponseEntity.status(HttpStatus.OK).build();

	}

	@RequestMapping(value = "/profile/{userName}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public ResponseEntity getUserProfile(HttpServletRequest httpServletRequest, @PathVariable("userName") String userName){

		HttpSession session = httpServletRequest.getSession(false);

		if((session == null) | (session != null && session.getAttribute("user") == null)){
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("Login To update your profile"));
		}

		if(!session.getAttribute("user").equals(userName)){
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("Not authorized to inquire given user"));
		}

		UserProfile userProfile = profileService.getUserProfile(userName);
		if(userProfile == null){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Not Found"));
		}

		httpServletRequest.changeSessionId();
		return ResponseEntity.status(HttpStatus.OK).body(userProfile);

	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity logout(HttpServletRequest httpServletRequest){
		
		HttpSession session = httpServletRequest.getSession(false);
		
		if(session != null){
			session.invalidate();
		}

		return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse("Successfully logged out"));

	}

}
