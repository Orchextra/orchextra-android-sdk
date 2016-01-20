package com.gigigo.orchextra.di.modules;

import android.content.Context;

import com.gigigo.ggglib.permissions.ContextProvider;
import com.gigigo.orchextra.android.applifecycle.DeviceRunningModeTypeImp;
import com.gigigo.orchextra.android.applifecycle.OrchextraActivityLifecycle;
import com.gigigo.orchextra.domain.device.DeviceRunningModeType;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 24/11/15.
 */
@Module(includes = {InteractorsModule.class, DomainModule.class})
public class OrchextraModule {

  private Context context;

  public OrchextraModule(Context context) {
    this.context = context;
  }

  @Provides @Singleton Context provideApplicationContext(){
    return context;
  }

  @Provides @Singleton
  DeviceRunningModeType provideDeviceRunningModeType() {
    return new DeviceRunningModeTypeImp();
  }

  @Provides
  @Singleton
  ContextProvider provideContextProvider(Context context) {
    return new OrchextraActivityLifecycle(context);
  }
}
