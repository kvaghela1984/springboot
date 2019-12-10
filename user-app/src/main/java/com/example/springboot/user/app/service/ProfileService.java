package com.example.springboot.user.app.service;

import com.example.springboot.user.app.entity.User;
import com.example.springboot.user.app.entity.UserProfile;
import com.example.springboot.user.app.models.AuthenticateRequest;
import com.example.springboot.user.app.models.ProfileUpdate;
import com.example.springboot.user.app.models.SignUpRequest;
import com.example.springboot.user.app.repository.ProfileRepository;
import com.example.springboot.user.app.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Observable;
import java.util.Optional;

@Service
public class ProfileService {

	@Autowired
	private ProfileRepository profileRepository;

	public void updateProfile(ProfileUpdate profileUpdate, String userName) {

		UserProfile profile = profileRepository.findById(userName).orElse(new UserProfile());

		if(profileUpdate.getFirstName() != null){
			profile.setFirstName(profileUpdate.getFirstName());
		}

		if(profileUpdate.getLastName() != null){
			profile.setLastName(profileUpdate.getLastName());
		}

		if(profileUpdate.getBirthDate() != null){
			profile.setBirthDate(Date.valueOf(profileUpdate.getBirthDate()));
		}

		if(profileUpdate.getPhone() != null){
			profile.setPhone(profileUpdate.getPhone());
		}

		if(profileUpdate.getIncome() != 0.0){
			profile.setIncome(profileUpdate.getIncome());
		}

		if(profileUpdate.getEmployer() != null){
			profile.setEmployer(profileUpdate.getEmployer());
		}

		if(profileUpdate.getAddressLine1() != null){
			profile.setAddressLine1(profileUpdate.getAddressLine1());
		}

		if(profileUpdate.getAddressLine2() != null){
			profile.setAddressLine2(profileUpdate.getAddressLine2());
		}

		if(profileUpdate.getCity() != null){
			profile.setCity(profileUpdate.getCity());
		}

		if(profileUpdate.getState() != null){
			profile.setState(profileUpdate.getState());
		}

		if(profileUpdate.getCountry() != null){
			profile.setCountry(profileUpdate.getCountry());
		}

		if(profileUpdate.getPostalCode() != null){
			profile.setPostalCode(profileUpdate.getPostalCode());
		}

		profile.setUserName(userName);
		profile.setUpdateTime(new Timestamp(Instant.now().toEpochMilli()));
		profileRepository.save(profile);

	}

	public UserProfile getUserProfile(String userName){
		UserProfile profile = profileRepository.findById(userName).orElse(null);
		return profile;
	}



}
