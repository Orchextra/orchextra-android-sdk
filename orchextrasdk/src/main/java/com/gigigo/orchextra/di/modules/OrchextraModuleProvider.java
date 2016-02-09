package com.gigigo.orchextra.di.modules;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.orchextra.di.modules.control.ControlModuleProvider;
import com.gigigo.orchextra.di.modules.device.DeviceModuleProvider;
import com.gigigo.orchextra.sdk.application.applifecycle.OrchextraActivityLifecycle;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
public interface OrchextraModuleProvider extends ControlModuleProvider, DeviceModuleProvider {
  OrchextraActivityLifecycle provideOrchextraActivityLifecycle();
  ContextProvider provideContextProvider();
}
