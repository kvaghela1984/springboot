package com.example.springboot.user.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.springboot.user.app.entity.User;

@Repository
public interface UsersRepository extends CrudRepository<User, String> {

}
