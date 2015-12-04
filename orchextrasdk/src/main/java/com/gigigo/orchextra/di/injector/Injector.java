package com.gigigo.orchextra.di.injector;

import com.gigigo.orchextra.delegates.AuthenticationDelegate;
import com.gigigo.orchextra.di.components.DelegateComponent;
import com.gigigo.orchextra.delegates.FakeDelegate;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/12/15.
 */
public interface Injector {
  DelegateComponent injectAuthDelegate(AuthenticationDelegate authenticationDelegate);
  DelegateComponent injectFakeDelegate(FakeDelegate fakeDelegate);
}
