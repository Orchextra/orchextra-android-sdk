package com.gigigo.orchextra.di.components;

import com.gigigo.orchextra.di.modules.device.ServicesModule;
import com.gigigo.orchextra.di.modules.device.ServicesModuleProvider;
import com.gigigo.orchextra.di.scopes.PerService;
import com.gigigo.orchextra.sdk.background.OrchextraBootBroadcastReceiver;
import orchextra.dagger.Component;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 7/3/16.
 */

@PerService @Component(dependencies = OrchextraComponent.class, modules = ServicesModule.class)
public interface OrchextraBootBroadcastReceiverComponent extends ServicesModuleProvider {
  void injectOrchextraBootBroadcastReceiver(OrchextraBootBroadcastReceiver orchextraBootBroadcastReceiver);
}
