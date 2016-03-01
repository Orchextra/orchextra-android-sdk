package com.gigigo.orchextra;

import android.app.Application;
import android.content.Context;

import com.gigigo.orchextra.control.controllers.authentication.SaveUserController;
import com.gigigo.orchextra.di.components.DaggerOrchextraComponent;
import com.gigigo.orchextra.di.components.OrchextraComponent;
import com.gigigo.orchextra.di.injector.InjectorImpl;
import com.gigigo.orchextra.di.modules.OrchextraModule;
import com.gigigo.orchextra.domain.abstractions.actions.CustomSchemeReceiver;
import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraCompletionCallback;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;
import com.gigigo.orchextra.domain.model.entities.authentication.Session;
import com.gigigo.orchextra.sdk.application.applifecycle.OrchextraActivityLifecycle;
import com.gigigo.orchextra.sdk.model.ORCUser;
import com.gigigo.orchextra.sdk.model.OrcUserToCrmConverter;

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

  @Inject
  Session session;

  @Inject
  OrcUserToCrmConverter orcUserToCrmConverter;

  @Inject
  SaveUserController saveUserController;

  private void initDependencyInjection(Context applicationContext, OrchextraCompletionCallback orchextraCompletionCallback) {
    OrchextraComponent orchextraComponent = DaggerOrchextraComponent.builder()
        .orchextraModule(new OrchextraModule(applicationContext, orchextraCompletionCallback))
        .build();
    injector = new InjectorImpl(orchextraComponent);
    orchextraComponent.injectOrchextra(Orchextra.instance);
  }

  public static synchronized void sdkInitialize(Application application, String apiKey,
      String apiSecret, OrchextraCompletionCallback orchextraCompletionCallback) {
    Orchextra.instance = new Orchextra();
    Orchextra.instance.init(application, orchextraCompletionCallback, apiKey, apiSecret);
  }

  private void init(Application application, OrchextraCompletionCallback orchextraCompletionCallback,
      String apiKey, String apiSecret) {

    initDependencyInjection(application.getApplicationContext(), orchextraCompletionCallback);

    session.setAppParams(apiKey, apiSecret);

    initLifecyle(application);

  }

  private void initLifecyle(Application application) {
    application.registerActivityLifecycleCallbacks(orchextraActivityLifecycle);
  }

  public static synchronized void setCustomSchemeReceiver(CustomSchemeReceiver customSchemeReceiver){
    OrchextraComponent orchextraComponent = getInjector().getOrchextraComponent();
    OrchextraModule orchextraModule = orchextraComponent.getOrchextraModule();
    orchextraModule.setCustomSchemeReceiver(customSchemeReceiver);
  }

  public static synchronized void setUser(ORCUser user) {
    OrcUserToCrmConverter orcUserToCrmConverter = Orchextra.instance.orcUserToCrmConverter;
    SaveUserController saveUserController = Orchextra.instance.saveUserController;

    Crm crm = orcUserToCrmConverter.convertOrcUserToCrm(user);
    saveUserController.saveUser(crm);
  }

  public static InjectorImpl getInjector() {
    return Orchextra.instance.injector;
  }

}
