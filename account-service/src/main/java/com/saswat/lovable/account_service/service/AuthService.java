package com.saswat.lovable.account_service.service;


import com.saswat.lovable.account_service.dto.auth.AuthResponse;
import com.saswat.lovable.account_service.dto.auth.LoginRequest;
import com.saswat.lovable.account_service.dto.auth.SignupRequest;

public interface AuthService {
    AuthResponse signup(SignupRequest request);

    AuthResponse login(LoginRequest request);
}
