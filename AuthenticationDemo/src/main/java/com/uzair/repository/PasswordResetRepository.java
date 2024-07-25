package com.uzair.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.uzair.entity.PasswordReset;
import java.util.List;


public interface PasswordResetRepository extends JpaRepository<PasswordReset, Integer> {

	public PasswordReset findByToken(String token);
	
	
	
	 @Query("SELECT p FROM PasswordReset p WHERE p.expiryDate <= CURRENT_TIMESTAMP")
	 List<PasswordReset> findExpiredTokens();
}
