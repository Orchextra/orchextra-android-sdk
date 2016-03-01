package com.gigigo.orchextra.di.components;

import com.gigigo.orchextra.delegates.ConfigDelegateImp;
import com.gigigo.orchextra.di.modules.device.DelegateModule;
import com.gigigo.orchextra.di.modules.device.DelegateModuleProvider;
import com.gigigo.orchextra.di.scopes.PerDelegate;

import dagger.Component;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/12/15.
 */
@PerDelegate @Component(dependencies = OrchextraComponent.class, modules = DelegateModule.class)
public interface DelegateComponent extends DelegateModuleProvider {
  void injectConfigDelegate(ConfigDelegateImp configDelegateImp);
}
