package com.gigigo.orchextra.domain.entities;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 22/12/15.
 */
public class Session {

    private String tokenType;
    private String tokenString;

    public Session(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getAuthToken() {
        return tokenType + " " + tokenString;
    }

    public void setTokenString(String tokenString) {
        this.tokenString = tokenString;
    }
}
