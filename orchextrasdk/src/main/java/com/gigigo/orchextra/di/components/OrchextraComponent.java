package com.gigigo.orchextra.di.components;

import com.gigigo.ggglib.permissions.ContextProvider;
import com.gigigo.orchextra.di.modules.OrchextraModule;
import com.gigigo.orchextra.di.modules.OrchextraModuleProvider;
import com.gigigo.orchextra.domain.device.DeviceRunningModeType;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 24/11/15.
 */
@Singleton @Component(modules = {OrchextraModule.class})
public interface OrchextraComponent extends OrchextraModuleProvider, DomainModuleProvider,
    InteractorsModuleProvider {

    DeviceRunningModeType provideDeviceRunningModeType();
    ContextProvider provideContextProvider();


}
