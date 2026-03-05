package com.coworking.coworking_technical_test.services.implementations;

import com.coworking.coworking_technical_test.entities.BlacklistedToken;
import com.coworking.coworking_technical_test.repositories.BlacklistedTokenRepository;
import com.coworking.coworking_technical_test.security.JwtTokenProvider;
import com.coworking.coworking_technical_test.services.interfaces.ITokenBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenBlacklistServiceImpl implements ITokenBlacklistService {

    private final BlacklistedTokenRepository blacklistedTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void blacklist(String token) {
        if (!blacklistedTokenRepository.existsByToken(token)) {
            LocalDateTime expiresAt = jwtTokenProvider.getExpirationFromToken(token);
            blacklistedTokenRepository.save(new BlacklistedToken(null, token, expiresAt));
        }
    }

    @Override
    public boolean isBlacklisted(String token) {
        return blacklistedTokenRepository.existsByToken(token);
    }
}
