package com.gigigo.orchextra.di.modules;

import com.gigigo.ggglib.network.executors.ApiServiceExecutor;
import com.gigigo.ggglib.network.mappers.ApiGenericResponseMapper;
import com.gigigo.ggglib.network.mappers.RequestMapper;
import com.gigigo.orchextra.dataprovision.authentication.datasource.AuthenticationDataSource;
import com.gigigo.orchextra.dataprovision.config.datasource.ConfigDataSource;
import com.gigigo.orchextra.di.qualifiers.ClientDataResponseMapper;
import com.gigigo.orchextra.di.qualifiers.ConfigRequestMapper;
import com.gigigo.orchextra.di.qualifiers.ConfigResponseMapper;
import com.gigigo.orchextra.di.qualifiers.SdkDataResponseMapper;
import dagger.Module;
import dagger.Provides;
import com.gigigo.orchextra.domain.device.DeviceDetailsProvider;
import gigigo.com.orchextra.data.datasources.api.auth.AuthenticationDataSourceImpl;
import gigigo.com.orchextra.data.datasources.api.config.ConfigDataSourceImpl;
import gigigo.com.orchextra.data.datasources.api.service.OrchextraApiService;
import gigigo.com.orchextra.data.datasources.device.DeviceDetailsFakeProviderImpl;
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
      @ConfigRequestMapper RequestMapper configRequest){

    return new ConfigDataSourceImpl(orchextraApiService, serviceExecutorProvider,
        configResponseMapper, configRequest);
  }

  @Provides @Singleton DeviceDetailsProvider provideDeviceDetailsProvider(){
    return new DeviceDetailsFakeProviderImpl();
  }
}
