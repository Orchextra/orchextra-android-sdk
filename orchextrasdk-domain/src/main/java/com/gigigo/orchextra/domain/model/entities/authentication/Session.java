package com.gigigo.orchextra.domain.model.entities.authentication;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 22/12/15.
 */
public class Session {

  private String apiKey;
  private String apiSecret;

  private String tokenType;
  private String tokenString;

  public Session(String tokenType) {
    this.tokenType = tokenType;
  }

  public void setAppParams(String apiKey, String apiSecret) {
    this.apiKey = apiKey;
    this.apiSecret = apiSecret;
  }

  public String getApiKey() {
    return apiKey;
  }

  public String getApiSecret() {
    return apiSecret;
  }

  public String getAuthToken() {
    return tokenType + " " + tokenString;
  }

  public void setTokenString(String tokenString) {
    this.tokenString = tokenString;
  }
}
