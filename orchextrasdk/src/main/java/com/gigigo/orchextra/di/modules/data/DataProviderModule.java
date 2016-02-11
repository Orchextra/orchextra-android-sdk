package com.gigigo.orchextra.di.modules.data;

import com.gigigo.orchextra.dataprovision.actions.ActionsDataProviderImpl;
import com.gigigo.orchextra.dataprovision.actions.datasource.ActionsDataSource;
import com.gigigo.orchextra.dataprovision.authentication.AuthenticationDataProviderImpl;
import com.gigigo.orchextra.dataprovision.authentication.datasource.AuthenticationDataSource;
import com.gigigo.orchextra.dataprovision.config.ConfigDataProviderImpl;
import com.gigigo.orchextra.dataprovision.config.datasource.ConfigDBDataSource;
import com.gigigo.orchextra.dataprovision.config.datasource.ConfigDataSource;
import com.gigigo.orchextra.dataprovision.authentication.datasource.SessionDBDataSource;
import com.gigigo.orchextra.dataprovision.proximity.ProximityLocalDataProviderImp;
import com.gigigo.orchextra.dataprovision.proximity.datasource.BeaconsDBDataSource;
import com.gigigo.orchextra.domain.dataprovider.ActionsDataProvider;
import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;
import com.gigigo.orchextra.domain.dataprovider.ConfigDataProvider;
import com.gigigo.orchextra.domain.dataprovider.ProximityLocalDataProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/12/15.
 */
@Module(includes = DataModule.class)
public class DataProviderModule {

  @Provides @Singleton AuthenticationDataProvider provideAuthenticationDataProvider(
      AuthenticationDataSource authenticationDataSource, SessionDBDataSource sessionDBDataSource){
    return new AuthenticationDataProviderImpl(authenticationDataSource, sessionDBDataSource);
  }

  @Provides @Singleton ConfigDataProvider provideConfigDataProvider(
      ConfigDataSource configDataSource, ConfigDBDataSource configDBDataSource){
    return new ConfigDataProviderImpl(configDataSource, configDBDataSource);
  }

  @Provides @Singleton ActionsDataProvider provideActionsDataProvider(
      ActionsDataSource actionsDataSource){
    return new ActionsDataProviderImpl(actionsDataSource);
  }

    @Provides @Singleton ProximityLocalDataProvider provideGeofenceDataProvider(ConfigDBDataSource configDBDataSource,
        BeaconsDBDataSource beaconsDBDataSource) {
        return new ProximityLocalDataProviderImp(configDBDataSource, beaconsDBDataSource);
    }
}
