package com.gigigo.orchextra.di.injector;

import com.gigigo.orchextra.delegates.AuthenticationDelegateImpl;
import com.gigigo.orchextra.di.components.DelegateComponent;
import com.gigigo.orchextra.delegates.FakeDelegate;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/12/15.
 */
public interface Injector {
  DelegateComponent injectAuthDelegate(AuthenticationDelegateImpl authenticationDelegate);
  DelegateComponent injectFakeDelegate(FakeDelegate fakeDelegate);
}