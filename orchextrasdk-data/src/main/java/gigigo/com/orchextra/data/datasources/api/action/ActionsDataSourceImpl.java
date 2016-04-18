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

package gigigo.com.orchextra.data.datasources.api.action;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.ggglib.mappers.ModelToExternalClassMapper;
import com.gigigo.ggglib.network.executors.ApiServiceExecutor;
import com.gigigo.ggglib.network.mappers.ApiGenericResponseMapper;
import com.gigigo.ggglib.network.responses.ApiGenericResponse;
import com.gigigo.orchextra.dataprovision.actions.datasource.ActionsDataSource;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.triggers.strategy.types.Trigger;

import java.util.Map;

import orchextra.javax.inject.Provider;

import gigigo.com.orchextra.data.datasources.api.model.responses.ApiActionResponse;
import gigigo.com.orchextra.data.datasources.api.service.OrchextraApiService;


public class ActionsDataSourceImpl implements ActionsDataSource {

  private final OrchextraApiService orchextraApiService;
  private final Provider<ApiServiceExecutor> serviceExecutorProvider;

  private final ModelToExternalClassMapper<Trigger, Map<String, String>> actionQueryParamsMapper;
  private final ApiGenericResponseMapper actionResponseMapper;

  public ActionsDataSourceImpl(OrchextraApiService orchextraApiService,
      Provider<ApiServiceExecutor> serviceExecutorProvider,
      ModelToExternalClassMapper actionQueryParamsMapper,
      ApiGenericResponseMapper actionResponseMapper) {

    this.orchextraApiService = orchextraApiService;
    this.serviceExecutorProvider = serviceExecutorProvider;
    this.actionQueryParamsMapper = actionQueryParamsMapper;
    this.actionResponseMapper = actionResponseMapper;
  }

  @Override public BusinessObject<BasicAction> obtainAction(Trigger actionCriteria) {
    ApiServiceExecutor serviceExecutor = serviceExecutorProvider.get();

    Map<String, String> queryParams = actionQueryParamsMapper.modelToExternalClass(actionCriteria);

    ApiGenericResponse apiGenericResponse =
        serviceExecutor.executeNetworkServiceConnection(ApiActionResponse.class,
            orchextraApiService.obtainAction(queryParams));

    return actionResponseMapper.mapApiGenericResponseToBusiness(apiGenericResponse);
  }
}
