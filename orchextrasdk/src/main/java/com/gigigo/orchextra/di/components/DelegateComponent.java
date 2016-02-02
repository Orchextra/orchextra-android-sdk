package com.gigigo.orchextra.di.components;

import com.gigigo.orchextra.delegates.AuthenticationDelegateImpl;
import com.gigigo.orchextra.delegates.ConfigDelegateImp;
import com.gigigo.orchextra.delegates.FakeDelegate;
import com.gigigo.orchextra.delegates.ProximityItemDelegateImp;
import com.gigigo.orchextra.di.modules.DelegateModule;
import com.gigigo.orchextra.di.scopes.PerDelegate;
import dagger.Component;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/12/15.
 */
@PerDelegate @Component(dependencies = OrchextraComponent.class, modules = DelegateModule.class)
public interface DelegateComponent {
  void injectAuhtDelegate(AuthenticationDelegateImpl authenticationDelegate);
  void injectFakeDelegate(FakeDelegate fakeDelegate);
  void injectProximityItemDelegate(ProximityItemDelegateImp proximityItemDelegate);
  void injectConfigDelegate(ConfigDelegateImp configDelegateImp);
}
