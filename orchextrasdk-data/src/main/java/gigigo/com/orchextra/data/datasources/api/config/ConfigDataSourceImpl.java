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

package gigigo.com.orchextra.data.datasources.api.config;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.ggglib.mappers.ModelToExternalClassMapper;
import com.gigigo.ggglib.network.executors.ApiServiceExecutor;
import com.gigigo.ggglib.network.mappers.ApiGenericResponseMapper;
import com.gigigo.ggglib.network.responses.ApiGenericResponse;
import com.gigigo.orchextra.dataprovision.config.datasource.ConfigDataSource;
import com.gigigo.orchextra.dataprovision.config.model.strategy.ConfigurationInfoResult;
import com.gigigo.orchextra.domain.model.config.ConfigRequest;
import gigigo.com.orchextra.data.datasources.api.model.requests.ApiConfigRequest;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiConfigData;
import gigigo.com.orchextra.data.datasources.api.service.OrchextraApiService;
import orchextra.javax.inject.Provider;


public class ConfigDataSourceImpl implements ConfigDataSource {

  private final OrchextraApiService orchextraApiService;
  private final Provider<ApiServiceExecutor> serviceExecutorProvider;

  private final ModelToExternalClassMapper<ConfigRequest, ApiConfigRequest>
      configModelToExternalClassMapper;
  private final ApiGenericResponseMapper configResponseMapper;

  public ConfigDataSourceImpl(OrchextraApiService orchextraApiService,
      Provider<ApiServiceExecutor> serviceExecutorProvider,
      ApiGenericResponseMapper configResponseMapper,
      ModelToExternalClassMapper configModelToExternalClassMapper) {

    this.orchextraApiService = orchextraApiService;
    this.serviceExecutorProvider = serviceExecutorProvider;
    this.configResponseMapper = configResponseMapper;
    this.configModelToExternalClassMapper = configModelToExternalClassMapper;
  }

  @Override public BusinessObject<ConfigurationInfoResult> sendConfigInfo(ConfigRequest configRequest) {
    ApiServiceExecutor serviceExecutor = serviceExecutorProvider.get();

    ApiConfigRequest request =
        configModelToExternalClassMapper.modelToExternalClass(configRequest);

    ApiGenericResponse apiGenericResponse =
        serviceExecutor.executeNetworkServiceConnection(ApiConfigData.class,
            orchextraApiService.sendSdkConfig(request));

    return configResponseMapper.mapApiGenericResponseToBusiness(apiGenericResponse);
  }
}
