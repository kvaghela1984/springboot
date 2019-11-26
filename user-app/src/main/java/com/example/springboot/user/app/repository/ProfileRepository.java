package com.example.springboot.user.app.repository;

import com.example.springboot.user.app.entity.User;
import com.example.springboot.user.app.entity.UserProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends CrudRepository<UserProfile, String> {

}
