package com.gigigo.orchextra.domain.entities;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/12/15.
 */
public class SdkAuthCredentials implements Credentials{

  private String apiKey;
  private String apiSecret;

  public SdkAuthCredentials(String apiKey, String apiSecret) {
    this.apiKey = apiKey;
    this.apiSecret = apiSecret;
  }

  public SdkAuthCredentials(User data) {

  }

  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public String getApiSecret() {
    return apiSecret;
  }

  public void setApiSecret(String apiSecret) {
    this.apiSecret = apiSecret;
  }
}
