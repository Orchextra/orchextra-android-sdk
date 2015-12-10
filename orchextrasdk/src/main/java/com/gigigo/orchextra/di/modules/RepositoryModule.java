package com.gigigo.orchextra.di.modules;

import com.gigigo.orchextra.dataprovision.authentication.AuthenticationRepositoryImpl;
import com.gigigo.orchextra.dataprovision.authentication.datasource.AuthenticationDataSource;
import com.gigigo.orchextra.domain.repository.AuthenticationRepository;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/12/15.
 */
@Module(includes = DataModule.class)
public class RepositoryModule {

  @Provides @Singleton AuthenticationRepository provideAuthenticationRepository(
      AuthenticationDataSource authenticationDataSource){
    return new AuthenticationRepositoryImpl(authenticationDataSource);
  }
}
