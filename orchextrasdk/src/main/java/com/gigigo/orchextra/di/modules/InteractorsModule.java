package com.gigigo.orchextra.di.modules;

import com.gigigo.orchextra.domain.dataprovider.ActionsDataProvider;
import com.gigigo.orchextra.domain.dataprovider.ConfigDataProvider;
import com.gigigo.orchextra.domain.device.DeviceDetailsProvider;
import com.gigigo.orchextra.domain.interactors.actions.GetActionInteractor;
import com.gigigo.orchextra.domain.interactors.authentication.AuthenticationInteractor;
import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;
import com.gigigo.orchextra.domain.interactors.config.SendConfigInteractor;
import com.gigigo.orchextra.domain.interactors.user.LogOnUserInteractor;
import com.gigigo.orchextra.domain.interactors.user.SaveUserInfoInteractor;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/12/15.
 */
@Module(includes = DataProviderModule.class)
public class InteractorsModule {

  @Provides @Singleton AuthenticationInteractor provideAuthenticationInteractor(
      AuthenticationDataProvider authenticationDataProvider, DeviceDetailsProvider deviceDetailsProvider){
    return new AuthenticationInteractor(authenticationDataProvider, deviceDetailsProvider);
  }

  @Provides @Singleton SendConfigInteractor provideSendConfigInteractor(
      ConfigDataProvider configDataProvider){
    return new SendConfigInteractor(configDataProvider);
  }

  @Provides @Singleton SaveUserInfoInteractor provideSaveUserInfoInteractor(
      ConfigDataProvider configDataProvider){
    return new SaveUserInfoInteractor(configDataProvider);
  }

  @Provides @Singleton LogOnUserInteractor provideSaveUserInfoInteractor(
      AuthenticationDataProvider authenticationDataProvider){
    return new LogOnUserInteractor(authenticationDataProvider);
  }

  @Provides @Singleton GetActionInteractor provideGetActionInteractor (
      ActionsDataProvider actionsDataProvider){
    return new GetActionInteractor(actionsDataProvider);
  }

}
