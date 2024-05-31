package com.products.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.products.entities.UserEntity;

public interface UserRespository extends JpaRepository<UserEntity, Long> {

	
	 UserEntity findByUsername(String username);
}
