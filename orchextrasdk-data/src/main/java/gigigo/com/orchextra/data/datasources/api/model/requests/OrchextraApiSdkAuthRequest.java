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


public class OrchextraApiSdkAuthRequest extends OrchextraApiAuthRequest {

  public OrchextraApiSdkAuthRequest(GrantType grantType, AuthCredentials authCredentials) {
    super(grantType, authCredentials);
  }

  @Override ApiCredentials obtainApiCredentialsFromCredentials(AuthCredentials authCredentials) {
    return new ApiSdkAuthCredentials(authCredentials);
  }
}
