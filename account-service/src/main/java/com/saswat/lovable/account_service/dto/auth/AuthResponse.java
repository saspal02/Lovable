package com.saswat.lovable.account_service.dto.auth;

public record AuthResponse(
        String token,
        UserProfileResponse user
) {

}
