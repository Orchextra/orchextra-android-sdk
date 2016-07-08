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

import com.gigigo.orchextra.domain.model.entities.credentials.AuthCredentials;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public abstract class OrchextraApiAuthRequest {

  @Expose @SerializedName("grantType") private final String grantType;

  @Expose @SerializedName("credentials") private final ApiCredentials credentials;

  public OrchextraApiAuthRequest(GrantType grantType, AuthCredentials authCredentials) {
    this.grantType = grantType.getStringValue();
    this.credentials = obtainApiCredentialsFromCredentials(authCredentials);
  }

  abstract ApiCredentials obtainApiCredentialsFromCredentials(AuthCredentials authCredentials);
}
