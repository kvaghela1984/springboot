package com.example.springboot.jwttutorial.repository;

import com.example.springboot.jwttutorial.entity.UserCredential;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserCredential, String> {

}
