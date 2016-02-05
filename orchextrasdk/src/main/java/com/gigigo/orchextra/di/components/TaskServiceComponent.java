package com.gigigo.orchextra.di.components;

import com.gigigo.orchextra.android.service.OrchextraGcmTaskService;
import com.gigigo.orchextra.di.modules.BackgroundModule;
import com.gigigo.orchextra.di.scopes.PerService;
import dagger.Component;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 5/2/16.
 */
@PerService @Component(dependencies = OrchextraComponent.class, modules = BackgroundModule.class)
public interface TaskServiceComponent {
  void injectTaskService(OrchextraGcmTaskService orchextraGcmTaskService);
}