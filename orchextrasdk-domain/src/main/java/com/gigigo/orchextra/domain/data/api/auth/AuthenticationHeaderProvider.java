package com.gigigo.orchextra.domain.data.api.auth;

public class AuthenticationHeaderProvider {

    private final String tokenType;
    private String authorizationToken;

    public AuthenticationHeaderProvider(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getAuthorizationToken() {
        return authorizationToken;
    }

    public void setAuthorizationToken(String value) {
        this.authorizationToken =  tokenType + " " + value;
    }
}
