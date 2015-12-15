package com.gigigo.orchextra.di.modules;

import com.gigigo.orchextra.dataprovision.authentication.AuthenticationDataProviderImpl;
import com.gigigo.orchextra.dataprovision.authentication.datasource.AuthenticationDataSource;
import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/12/15.
 */
@Module(includes = DataModule.class)
public class DataProviderModule {

  @Provides @Singleton AuthenticationDataProvider provideAuthenticationRepository(
      AuthenticationDataSource authenticationDataSource){
    return new AuthenticationDataProviderImpl(authenticationDataSource);
  }
}
