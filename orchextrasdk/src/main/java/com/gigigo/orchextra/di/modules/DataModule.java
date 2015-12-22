package com.gigigo.orchextra.di.modules;

import android.content.Context;
import com.gigigo.ggglib.network.executors.ApiServiceExecutor;
import com.gigigo.ggglib.network.mappers.ApiGenericResponseMapper;
import com.gigigo.ggglib.network.mappers.RequestMapper;
import com.gigigo.orchextra.dataprovision.actions.datasource.ActionsDataSource;
import com.gigigo.orchextra.dataprovision.authentication.datasource.AuthenticationDataSource;
import com.gigigo.orchextra.dataprovision.config.datasource.ConfigDataSource;
import com.gigigo.orchextra.di.qualifiers.ActionQueryRequest;
import com.gigigo.orchextra.di.qualifiers.ActionsResponse;
import com.gigigo.orchextra.di.qualifiers.ClientDataResponseMapper;
import com.gigigo.orchextra.di.qualifiers.ConfigRequest;
import com.gigigo.orchextra.di.qualifiers.ConfigResponseMapper;
import com.gigigo.orchextra.di.qualifiers.SdkDataResponseMapper;
import dagger.Module;
import dagger.Provides;
import com.gigigo.orchextra.domain.device.DeviceDetailsProvider;
import gigigo.com.orchextra.data.datasources.api.action.ActionsDataSourceImpl;
import gigigo.com.orchextra.data.datasources.api.auth.AuthenticationDataSourceImpl;
import gigigo.com.orchextra.data.datasources.api.config.ConfigDataSourceImpl;
import gigigo.com.orchextra.data.datasources.api.service.OrchextraApiService;
import gigigo.com.orchextra.data.datasources.device.DeviceDetailsProviderImpl;
import javax.inject.Provider;
import javax.inject.Singleton;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 24/11/15.
 */
@Module(includes = {ApiModule.class, ApiMappersModule.class})
public class DataModule {

  @Provides @Singleton AuthenticationDataSource provideAuthenticationDataSource(
      OrchextraApiService orchextraApiService,
      Provider<ApiServiceExecutor> serviceExecutorProvider,
      @SdkDataResponseMapper ApiGenericResponseMapper sdkResponseMapper,
      @ClientDataResponseMapper ApiGenericResponseMapper clientResponseMapper){

    return new AuthenticationDataSourceImpl(orchextraApiService, serviceExecutorProvider,
        sdkResponseMapper, clientResponseMapper);
  }

  @Provides @Singleton ConfigDataSource provideConfigDataSource(
      OrchextraApiService orchextraApiService,
      Provider<ApiServiceExecutor> serviceExecutorProvider,
      @ConfigResponseMapper ApiGenericResponseMapper configResponseMapper,
      @ConfigRequest RequestMapper configRequest){

    return new ConfigDataSourceImpl(orchextraApiService, serviceExecutorProvider,
        configResponseMapper, configRequest);
  }

  @Provides @Singleton ActionsDataSource provideActionsDataSource(
      OrchextraApiService orchextraApiService,
      Provider<ApiServiceExecutor> serviceExecutorProvider,
      @ActionQueryRequest RequestMapper queryRequestMapper,
      @ActionsResponse ApiGenericResponseMapper actionsResponseMapper){

    return new ActionsDataSourceImpl(orchextraApiService, serviceExecutorProvider,
        queryRequestMapper, actionsResponseMapper);
  }

  @Provides @Singleton DeviceDetailsProvider provideDeviceDetailsProvider(Context context){
    return new DeviceDetailsProviderImpl(context);
  }

}
