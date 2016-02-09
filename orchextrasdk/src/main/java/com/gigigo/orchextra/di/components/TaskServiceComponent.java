package com.gigigo.orchextra.di.components;

import com.gigigo.orchextra.di.modules.OrchextraModuleProvider;
import com.gigigo.orchextra.di.modules.device.ServicesModuleProvider;
import com.gigigo.orchextra.di.modules.device.ServicesModule;
import com.gigigo.orchextra.sdk.background.OrchextraGcmTaskService;
import com.gigigo.orchextra.di.scopes.PerService;
import dagger.Component;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 5/2/16.
 */
@PerService @Component(dependencies = OrchextraComponent.class, modules = ServicesModule.class)
public interface TaskServiceComponent extends ServicesModuleProvider{
  void injectTaskService(OrchextraGcmTaskService orchextraGcmTaskService);
}