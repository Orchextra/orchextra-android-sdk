package com.gigigo.orchextra.domain.data.api.auth;

public class AuthenticationHeaderProvider {

    private String authorizationToken;

    public String getAuthorizationToken() {
        return authorizationToken;
    }

    public void setAuthorizationToken(String value) {
        this.authorizationToken = "Bearer " + value;
    }
}
