package com.gigigo.orchextra.di.modules;

import com.gigigo.orchextra.dataprovision.actions.ActionsDataProviderImpl;
import com.gigigo.orchextra.dataprovision.actions.datasource.ActionsDataSource;
import com.gigigo.orchextra.dataprovision.authentication.AuthenticationDataProviderImpl;
import com.gigigo.orchextra.dataprovision.authentication.datasource.AuthenticationDataSource;
import com.gigigo.orchextra.dataprovision.config.ConfigDataProviderImpl;
import com.gigigo.orchextra.dataprovision.config.datasource.ConfigDataSource;
import com.gigigo.orchextra.domain.dataprovider.ActionsDataProvider;
import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;
import com.gigigo.orchextra.domain.dataprovider.ConfigDataProvider;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/12/15.
 */
@Module(includes = DataModule.class)
public class DataProviderModule {

  @Provides @Singleton AuthenticationDataProvider provideAuthenticationDataProvider(
      AuthenticationDataSource authenticationDataSource){
    return new AuthenticationDataProviderImpl(authenticationDataSource);
  }

  @Provides @Singleton ConfigDataProvider provideConfigDataProvider(
      ConfigDataSource configDataSource){
    return new ConfigDataProviderImpl(configDataSource);
  }

  @Provides @Singleton ActionsDataProvider provideActionsDataProvider(
      ActionsDataSource actionsDataSource){
    return new ActionsDataProviderImpl(actionsDataSource);
  }
}
