package com.gigigo.orchextra;

import android.app.Application;
import android.content.Context;

import com.gigigo.orchextra.delegates.AuthenticationDelegateImpl;
import com.gigigo.orchextra.delegates.FakeDelegate;
import com.gigigo.orchextra.di.components.DaggerOrchextraComponent;
import com.gigigo.orchextra.di.components.OrchextraComponent;
import com.gigigo.orchextra.di.injector.InjectorImpl;
import com.gigigo.orchextra.di.modules.OrchextraModule;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 23/11/15.
 */
public class Orchextra {

  private static Context applicationContext;
  private static InjectorImpl injector;
  private static OrchextraComponent orchextraComponent;
  private static OrchextraActivityLifecycle orchextraLifecycle;

  private static void initDependencyInjection() {
    orchextraComponent = DaggerOrchextraComponent.builder()
        .orchextraModule(new OrchextraModule(Orchextra.applicationContext)).build();

    injector = new InjectorImpl(orchextraComponent);
  }

  public static synchronized void sdkInitialize(Context applicationContext,
      String apiKey, String apiSecret) {

    Orchextra.applicationContext = applicationContext.getApplicationContext();

    initDependencyInjection();

    checkSdkBasicPermissions();

    startLifeCycle();

    authenticate(apiKey, apiSecret);

    startSdkServices();
  }

  private static void authenticate(String apiKey, String apiSecret) {
    AuthenticationDelegateImpl.authenticate(apiKey, apiSecret);
    FakeDelegate.showToast();
  }

  private static void checkSdkBasicPermissions() {

  }

  private static void startSdkServices() {

  }

  private static void startLifeCycle() {
    Application app = (Application) applicationContext;
    orchextraLifecycle = new OrchextraActivityLifecycle();
    app.registerActivityLifecycleCallbacks(orchextraLifecycle);
  }

  public static OrchextraActivityLifecycle getOchextraLifeCycle() {
    return orchextraLifecycle;
  }

  public static InjectorImpl getInjector() {
    return injector;
  }
}
