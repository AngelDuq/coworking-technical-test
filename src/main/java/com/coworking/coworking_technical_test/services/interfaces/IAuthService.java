package com.coworking.coworking_technical_test.services.interfaces;

import com.coworking.coworking_technical_test.shared.request.LoginRequest;
import com.coworking.coworking_technical_test.shared.responses.TokenResponse;

public interface IAuthService {

    TokenResponse login(LoginRequest loginRequest);

    void logout(String authHeader);
}
