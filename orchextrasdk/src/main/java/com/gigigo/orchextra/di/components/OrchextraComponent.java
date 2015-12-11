package com.gigigo.orchextra.di.components;

import com.gigigo.orchextra.di.modules.OrchextraModule;
import com.gigigo.orchextra.di.modules.OrchextraModuleProvider;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 24/11/15.
 */
@Singleton @Component(modules = {OrchextraModule.class})
public interface OrchextraComponent extends OrchextraModuleProvider, DomainModuleProvider,
    InteractorsModuleProvider {
}
