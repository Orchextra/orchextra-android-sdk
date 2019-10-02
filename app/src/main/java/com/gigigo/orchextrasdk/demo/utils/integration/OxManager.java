/*
 * Created by Orchextra
 *
 * Copyright (C) 2017 Gigigo Mobile Services SL
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

package com.gigigo.orchextrasdk.demo.utils.integration;

import android.app.Application;
import androidx.annotation.NonNull;
import java.util.List;

public interface OxManager {

  void startImageRecognition();

  void startScanner();

  void init(Application application, Config config, StatusListener statusListener);

  void finish();

  void removeListeners();

  Boolean isReady();

  void getToken(TokenReceiver tokenReceiver);

  void setErrorListener(ErrorListener errorListener);

  void setBusinessUnits(List<String> businessUnits);

  void setCustomSchemeReceiver(CustomActionListener customSchemeReceiver);

  interface TokenReceiver {
    void onGetToken(@NonNull String token);
  }

  interface CustomActionListener {
    void onCustomSchema(@NonNull String customSchema);
  }

  interface StatusListener {
    void isReady();

    void onError(@NonNull String error);
  }

  interface ErrorListener {
    void onError(@NonNull String error);
  }

  final class Config {

    private final String apiKey;
    private final String apiSecret;
    private final String firebaseApiKey;
    private final String firebaseApplicationId;

    private Config(Builder builder) {
      apiKey = builder.apiKey;
      apiSecret = builder.apiSecret;
      firebaseApiKey = builder.firebaseApiKey;
      firebaseApplicationId = builder.firebaseApplicationId;
    }

    public String getApiKey() {
      return apiKey;
    }

    public String getApiSecret() {
      return apiSecret;
    }

    public String getFirebaseApiKey() {
      return firebaseApiKey;
    }

    public String getFirebaseApplicationId() {
      return firebaseApplicationId;
    }

    public static final class Builder {
      private String apiKey;
      private String apiSecret;
      private String firebaseApiKey;
      private String firebaseApplicationId;

      public Builder() {
      }

      public Builder apiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
      }

      public Builder apiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
        return this;
      }

      public Builder firebaseApiKey(String firebaseApiKey) {
        this.firebaseApiKey = firebaseApiKey;
        return this;
      }

      public Builder firebaseApplicationId(String firebaseApplicationId) {
        this.firebaseApplicationId = firebaseApplicationId;
        return this;
      }

      public Config build() {
        return new Config(this);
      }
    }
  }
}
