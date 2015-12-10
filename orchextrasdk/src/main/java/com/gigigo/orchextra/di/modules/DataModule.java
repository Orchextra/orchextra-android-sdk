package com.gigigo.orchextra.di.modules;

import com.gigigo.ggglib.network.executors.ApiServiceExecutor;
import com.gigigo.orchextra.dataprovision.authentication.datasource.AuthenticationDataSource;
import dagger.Module;
import dagger.Provides;
import gigigo.com.orchextra.data.datasources.datasources.api.auth.AuthenticationDataSourceImpl;
import gigigo.com.orchextra.data.datasources.datasources.api.service.OrchextraApiService;
import javax.inject.Provider;
import javax.inject.Singleton;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 24/11/15.
 */
@Module(includes = ApiModule.class)
public class DataModule {

  @Provides @Singleton AuthenticationDataSource provideAuthenticationDataSource(
      OrchextraApiService orchextraApiService,
      Provider<ApiServiceExecutor> serviceExecutorProvider){
    return new AuthenticationDataSourceImpl(orchextraApiService, serviceExecutorProvider);
  }
}
