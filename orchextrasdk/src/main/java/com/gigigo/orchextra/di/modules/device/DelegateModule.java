package com.gigigo.orchextra.di.modules.device;

import com.gigigo.orchextra.control.controllers.authentication.AuthenticationController;
import com.gigigo.orchextra.control.controllers.config.ConfigController;
import com.gigigo.orchextra.delegates.AuthenticationDelegateImpl;
import com.gigigo.orchextra.delegates.ConfigDelegateImp;
import com.gigigo.orchextra.device.geolocation.geocoder.AndroidGeolocationManager;
import com.gigigo.orchextra.device.information.AndroidApp;
import com.gigigo.orchextra.device.information.AndroidDevice;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/12/15.
 */
@Module
public class DelegateModule {

  @Provides @Singleton ConfigDelegateImp provideConfigDelegateImp(ConfigController configController,
      AndroidApp androidApp, AndroidDevice androidDevice,
      AndroidGeolocationManager androidGeolocationManager){
    return new ConfigDelegateImp(configController, androidApp, androidDevice, androidGeolocationManager);
  }


  @Provides @Singleton AuthenticationDelegateImpl provideAuthenticationDelegateImpl(
      AuthenticationController authenticationController){
    return new AuthenticationDelegateImpl(authenticationController);
  }




}
