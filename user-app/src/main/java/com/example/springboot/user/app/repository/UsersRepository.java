package com.example.springboot.user.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.springboot.user.app.entity.UserEntity;

@Repository
public interface UsersRepository extends CrudRepository<UserEntity, String> {

}
