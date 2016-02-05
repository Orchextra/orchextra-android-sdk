package com.gigigo.orchextra.di.injector;

import com.gigigo.orchextra.android.service.OrchextraBackgroundService;
import com.gigigo.orchextra.android.service.OrchextraGcmTaskService;
import com.gigigo.orchextra.delegates.AuthenticationDelegateImpl;
import com.gigigo.orchextra.di.components.DelegateComponent;
import com.gigigo.orchextra.delegates.FakeDelegate;
import com.gigigo.orchextra.di.components.ServiceComponent;
import com.gigigo.orchextra.di.components.TaskServiceComponent;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/12/15.
 */
public interface Injector {
  DelegateComponent injectAuthDelegate(AuthenticationDelegateImpl authenticationDelegate);
  DelegateComponent injectFakeDelegate(FakeDelegate fakeDelegate);
  ServiceComponent injectServiceComponent (OrchextraBackgroundService myAppService);
  TaskServiceComponent injectTaskServiceComponent(OrchextraGcmTaskService orchextraGcmTaskService);
}
