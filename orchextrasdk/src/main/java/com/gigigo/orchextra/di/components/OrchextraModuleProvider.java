package com.gigigo.orchextra.di.components;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.orchextra.android.ViewActionDispatcher;
import com.gigigo.orchextra.android.applifecycle.OrchextraActivityLifecycle;
import com.gigigo.orchextra.domain.device.AppRunningMode;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
public interface OrchextraModuleProvider {
  AppRunningMode provideAppRunningModeType();
  ContextProvider provideContextProvider();
  ViewActionDispatcher provideViewActionDispatcher();
  OrchextraActivityLifecycle provideOrchextraActivityLifecycle();
}
