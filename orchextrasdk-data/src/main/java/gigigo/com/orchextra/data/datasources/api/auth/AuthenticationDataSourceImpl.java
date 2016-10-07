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

package gigigo.com.orchextra.data.datasources.api.auth;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.ggglib.network.executors.ApiServiceExecutor;
import com.gigigo.ggglib.network.mappers.ApiGenericResponseMapper;
import com.gigigo.ggglib.network.responses.ApiGenericResponse;
import com.gigigo.orchextra.dataprovision.authentication.datasource.AuthenticationDataSource;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.SdkAuthData;
import com.gigigo.orchextra.domain.model.entities.credentials.AuthCredentials;
import gigigo.com.orchextra.data.datasources.api.model.requests.GrantType;
import gigigo.com.orchextra.data.datasources.api.model.requests.ApiAuthRequest;
import gigigo.com.orchextra.data.datasources.api.model.requests.ApiClientAuthRequest;
import gigigo.com.orchextra.data.datasources.api.model.requests.ApiSdkAuthRequest;
import gigigo.com.orchextra.data.datasources.api.service.OrchextraApiService;
import orchextra.javax.inject.Provider;


public class AuthenticationDataSourceImpl implements AuthenticationDataSource {

  private final OrchextraApiService orchextraApiService;
  private final Provider<ApiServiceExecutor> serviceExecutorProvider;

  private final ApiGenericResponseMapper sdkResponseMapper;
  private final ApiGenericResponseMapper clientResponseMapper;

  public AuthenticationDataSourceImpl(OrchextraApiService orchextraApiService,
      Provider<ApiServiceExecutor> serviceExecutorProvider,
      ApiGenericResponseMapper sdkResponseMapper, ApiGenericResponseMapper clientResponseMapper) {
    this.orchextraApiService = orchextraApiService;
    this.serviceExecutorProvider = serviceExecutorProvider;
    this.sdkResponseMapper = sdkResponseMapper;
    this.clientResponseMapper = clientResponseMapper;
  }

  @Override public BusinessObject<SdkAuthData> authenticateSdk(AuthCredentials authCredentials) {
    ApiServiceExecutor serviceExecutor = serviceExecutorProvider.get();

    ApiAuthRequest request =
        new ApiSdkAuthRequest(GrantType.AUTH_SDK, authCredentials);

    ApiGenericResponse apiGenericResponse =
        serviceExecutor.executeNetworkServiceConnection(SdkAuthData.class,
            orchextraApiService.sdkAuthentication(request));

    return sdkResponseMapper.mapApiGenericResponseToBusiness(apiGenericResponse);
  }

  @Override public BusinessObject<ClientAuthData> authenticateUser(AuthCredentials authCredentials) {
    ApiServiceExecutor serviceExecutor = serviceExecutorProvider.get();

    ApiAuthRequest request =
        new ApiClientAuthRequest(GrantType.AUTH_USER, authCredentials);

    ApiGenericResponse apiGenericResponse =
        serviceExecutor.executeNetworkServiceConnection(SdkAuthData.class,
            orchextraApiService.clientAuthentication(request));

    return clientResponseMapper.mapApiGenericResponseToBusiness(apiGenericResponse);
  }
}
