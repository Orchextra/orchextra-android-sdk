package com.gigigo.orchextra.di.components;

import com.gigigo.orchextra.delegates.AuthenticationDelegate;
import com.gigigo.orchextra.delegates.FakeDelegate;
import com.gigigo.orchextra.di.qualifiers.PerDelegate;
import dagger.Component;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/12/15.
 */
@PerDelegate @Component(dependencies = OrchextraComponent.class)
public interface DelegateComponent {
  void injectAuhtDelegate(AuthenticationDelegate authenticationDelegate);
  void injectFakeDelegate(FakeDelegate fakeDelegate);
}
