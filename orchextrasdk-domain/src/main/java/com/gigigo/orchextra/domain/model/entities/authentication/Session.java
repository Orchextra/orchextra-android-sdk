/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigigo.orchextra.domain.model.entities.authentication;

public class Session {

  private String apiKey;
  private String apiSecret;

  private String tokenType;
  private String tokenString;
  private String accessToken;

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
    return accessToken;
  }

  public void setTokenString(String tokenString) {
    this.tokenString = tokenString;
    this.accessToken = tokenType + " " + tokenString;
    System.out.println("REALM ************ setTokenString tokenstring: \n"+this.tokenString +"\naccesstoken:\n"+accessToken);
  }

  public String getTokenString() {
    return tokenString;
  }
}
