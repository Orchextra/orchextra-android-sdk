package com.gigigo.orchextra.di.components;

import com.gigigo.orchextra.sdk.OrchextraManager;
import com.gigigo.orchextra.di.modules.OrchextraModule;

import com.gigigo.orchextra.di.modules.OrchextraModuleProvider;
import com.gigigo.orchextra.di.modules.domain.InteractorsModule;
import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 24/11/15.
 */
@Singleton @Component(modules = {OrchextraModule.class})
public interface OrchextraComponent extends OrchextraModuleProvider {
    void injectOrchextra(OrchextraManager orchextra);
    InteractorExecutionComponent plus(InteractorsModule interactorsModule);
}
