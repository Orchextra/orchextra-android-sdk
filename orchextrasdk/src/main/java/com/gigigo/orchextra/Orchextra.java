package com.gigigo.orchextra;

import android.app.Application;
import android.content.Context;

import com.gigigo.orchextra.android.applifecycle.OrchextraActivityLifecycle;
import com.gigigo.orchextra.delegates.AuthenticationDelegateImpl;
import com.gigigo.orchextra.delegates.FakeDelegate;
import com.gigigo.orchextra.di.components.DaggerOrchextraComponent;
import com.gigigo.orchextra.di.components.OrchextraComponent;
import com.gigigo.orchextra.di.injector.InjectorImpl;
import com.gigigo.orchextra.di.modules.OrchextraModule;
import com.gigigo.orchextra.initalization.OrchextraCompletionCallback;
import javax.inject.Inject;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 23/11/15.
 */
public class Orchextra {

  private static Orchextra instance;
  private InjectorImpl injector;

  @Inject
  OrchextraActivityLifecycle orchextraActivityLifecycle;

  public void initDependencyInjection(Context applicationContext, OrchextraCompletionCallback orchextraCompletionCallback) {
    OrchextraComponent orchextraComponent = DaggerOrchextraComponent.builder()
        .orchextraModule(new OrchextraModule(applicationContext, orchextraCompletionCallback))
        .build();
    injector = new InjectorImpl(orchextraComponent);
    orchextraComponent.injectOrchextra(Orchextra.instance);
  }

  public static synchronized void sdkInitialize(Application application, String apiKey,
      String apiSecret, OrchextraCompletionCallback orchextraCompletionCallback) {
    Orchextra.instance = new Orchextra();
    Orchextra.instance.init(application, orchextraCompletionCallback);
    Orchextra.instance.start(apiKey, apiSecret);
  }

  private void init(Application application, OrchextraCompletionCallback orchextraCompletionCallback) {
    initDependencyInjection(application.getApplicationContext(), orchextraCompletionCallback);

    initLifecyle(application);

  }

  private void initLifecyle(Application application) {
    application.registerActivityLifecycleCallbacks(orchextraActivityLifecycle);
  }

  private void start(String apiKey, String apiSecret) {
    authenticate(apiKey, apiSecret);
  }

  private void authenticate(String apiKey, String apiSecret) {
    AuthenticationDelegateImpl.authenticate(apiKey, apiSecret);
    FakeDelegate.showToast();
  }

  public static InjectorImpl getInjector() {
    return Orchextra.instance.injector;
  }

}
