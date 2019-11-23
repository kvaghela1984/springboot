package com.example.springboot.user.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.springboot.user.app.entity.UserEntity;

public interface UsersRepository extends CrudRepository<UserEntity, String> {

}
