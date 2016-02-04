package com.gigigo.orchextra.di.components;

import com.gigigo.orchextra.android.service.OrchextraBackgroundService;
import com.gigigo.orchextra.di.modules.BackgroundModule;
import com.gigigo.orchextra.di.scopes.PerService;
import dagger.Component;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 24/11/15.
 */
@PerService @Component(dependencies = OrchextraComponent.class, modules = BackgroundModule.class)
public interface ServiceComponent {
    void injectOrchextraService(OrchextraBackgroundService myAppService);
}
