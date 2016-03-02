package com.gigigo.orchextra.domain.model.entities.credentials;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/12/15.
 */
public class SdkAuthCredentials implements Credentials {

  private final String apiKey;
  private final String apiSecret;

  public SdkAuthCredentials(String apiKey, String apiSecret) {
    this.apiKey = apiKey;
    this.apiSecret = apiSecret;
  }

  public String getApiKey() {
    return apiKey;
  }

  public String getApiSecret() {
    return apiSecret;
  }
}
