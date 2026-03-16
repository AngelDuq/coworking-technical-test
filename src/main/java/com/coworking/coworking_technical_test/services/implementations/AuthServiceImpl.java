package com.coworking.coworking_technical_test.services.implementations;

import com.coworking.coworking_technical_test.security.JwtTokenProvider;
import com.coworking.coworking_technical_test.services.interfaces.IAuthService;
import com.coworking.coworking_technical_test.services.interfaces.ITokenBlacklistService;
import com.coworking.coworking_technical_test.shared.request.LoginRequest;
import com.coworking.coworking_technical_test.shared.responses.TokenResponse;
import com.coworking.coworking_technical_test.shared.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final ITokenBlacklistService tokenBlacklistService;

    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        String rol = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
            .orElse(Constants.AUTHORITY_ROLE_USER);

        return new TokenResponse(token, rol);
    }

    @Override
    public void logout(String authHeader) {
        String token = authHeader.substring(Constants.TOKEN_BEARER_PREFIX.length()).trim();
        tokenBlacklistService.blacklist(token);
    }
}
