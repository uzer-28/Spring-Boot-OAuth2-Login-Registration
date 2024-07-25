package com.uzair.cleanup;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.uzair.entity.PasswordReset;
import com.uzair.repository.PasswordResetRepository;

@Component                                 //class for deleting the expired token from the database.    
public class PasswordResetCleanupTask {

	@Autowired
    private PasswordResetRepository passwordResetRepository;

    @Scheduled(fixedRate = 600000) //invoke the method on given fixedrate.
    @Transactional
    public void cleanUpExpiredTokens() {
        List<PasswordReset> expiredTokens = passwordResetRepository.findExpiredTokens();
        if (!expiredTokens.isEmpty()) {
            passwordResetRepository.deleteAll(expiredTokens);
        }
    }
}

