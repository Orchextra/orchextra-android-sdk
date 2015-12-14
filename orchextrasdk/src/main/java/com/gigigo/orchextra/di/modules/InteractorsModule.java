package com.gigigo.orchextra.di.modules;

import com.gigigo.orchextra.domain.device.DeviceDetailsProvider;
import com.gigigo.orchextra.domain.interactors.authentication.AuthenticationInteractor;
import com.gigigo.orchextra.domain.repository.AuthenticationRepository;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/12/15.
 */
@Module(includes = RepositoryModule.class)
public class InteractorsModule {

  @Provides @Singleton AuthenticationInteractor provideAuthenticationInteractor(
      AuthenticationRepository authenticationRepository, DeviceDetailsProvider deviceDetailsProvider){
    return new AuthenticationInteractor(authenticationRepository, deviceDetailsProvider);
  }
}
