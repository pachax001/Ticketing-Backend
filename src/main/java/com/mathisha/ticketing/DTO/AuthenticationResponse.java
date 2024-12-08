package com.mathisha.ticketing.DTO;

import java.util.UUID;

public class AuthenticationResponse {
    private String jwt;

    private UUID userId;

    public String getJwt() {
        return jwt;
    }
    public UUID getUserId() {
        return userId;
    }


    public AuthenticationResponse(String jwt, UUID userId) {
        this.jwt = jwt;
        this.userId = userId;
    }
}
