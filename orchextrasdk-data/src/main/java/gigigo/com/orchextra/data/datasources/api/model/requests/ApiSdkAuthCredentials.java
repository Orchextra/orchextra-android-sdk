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

package gigigo.com.orchextra.data.datasources.api.model.requests;

import com.gigigo.gggjavalib.general.utils.ConsistencyUtils;
import com.gigigo.orchextra.domain.model.entities.credentials.Credentials;
import com.gigigo.orchextra.domain.model.entities.credentials.SdkAuthCredentials;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ApiSdkAuthCredentials implements ApiCredentials {

  @Expose @SerializedName("apiKey") private final String apiKey;

  @Expose @SerializedName("apiSecret") private final String apiSecret;

  public ApiSdkAuthCredentials(Credentials credentials) {
    SdkAuthCredentials sdkCredentials =
        ConsistencyUtils.checkInstance(credentials, SdkAuthCredentials.class);
    this.apiKey = sdkCredentials.getApiKey();
    this.apiSecret = sdkCredentials.getApiSecret();
  }

  public String getApiKey() {
    return apiKey;
  }

  public String getApiSecret() {
    return apiSecret;
  }
}
