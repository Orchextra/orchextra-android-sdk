package com.gigigo.orchextra.di.modules.device;

import com.gigigo.orchextra.control.controllers.config.ConfigController;
import com.gigigo.orchextra.delegates.ConfigDelegateImp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/12/15.
 */
@Module
public class DelegateModule {

  @Provides @Singleton ConfigDelegateImp provideConfigDelegateImp(ConfigController configController){
    return new ConfigDelegateImp(configController);
  }
}
