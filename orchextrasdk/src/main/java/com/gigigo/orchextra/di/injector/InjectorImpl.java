package com.gigigo.orchextra.di.injector;

import com.gigigo.orchextra.delegates.AuthenticationDelegateImpl;
import com.gigigo.orchextra.delegates.proximity.ProximityItemDelegateImp;
import com.gigigo.orchextra.di.components.DaggerDelegateComponent;
import com.gigigo.orchextra.di.components.DelegateComponent;
import com.gigigo.orchextra.delegates.FakeDelegate;
import com.gigigo.orchextra.di.components.OrchextraComponent;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/12/15.
 */
public class InjectorImpl implements Injector{

  private OrchextraComponent orchextraComponent;

  public InjectorImpl(OrchextraComponent orchextraComponent) {
    this.orchextraComponent = orchextraComponent;
  }

  @Override synchronized public DelegateComponent injectAuthDelegate(AuthenticationDelegateImpl authenticationDelegate) {
    DelegateComponent delegateComponent = DaggerDelegateComponent.builder().
        orchextraComponent(orchextraComponent).build();
    delegateComponent.injectAuhtDelegate(authenticationDelegate);
    return delegateComponent;
  }

  @Override synchronized public DelegateComponent injectFakeDelegate(FakeDelegate fakeDelegate) {
    DelegateComponent delegateComponent = DaggerDelegateComponent.builder().
        orchextraComponent(orchextraComponent).build();
    delegateComponent.injectFakeDelegate(fakeDelegate);
    return delegateComponent;
  }

  public DelegateComponent injectProximityItemDelegate(ProximityItemDelegateImp proximityItemDelegateImp) {
    DelegateComponent delegateComponent = DaggerDelegateComponent.builder().
            orchextraComponent(orchextraComponent).build();
    delegateComponent.injectProximityItemDelegate(proximityItemDelegateImp);
    return delegateComponent;
    return null;
  }
}
