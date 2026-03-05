package com.coworking.coworking_technical_test.services.interfaces;

public interface ITokenBlacklistService {

    void blacklist(String token);

    boolean isBlacklisted(String token);
}
