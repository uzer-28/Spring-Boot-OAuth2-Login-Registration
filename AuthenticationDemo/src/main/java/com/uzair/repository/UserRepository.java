package com.uzair.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uzair.entity.UserEntity;
import java.util.List;


public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	public UserEntity findByEmail(String email);
}
