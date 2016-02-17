package com.gigigo.orchextra.di.components;

import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.di.modules.domain.InteractorsModule;
import com.gigigo.orchextra.di.modules.domain.InteractorsModuleProvider;
import com.gigigo.orchextra.di.scopes.PerExecution;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;
import dagger.Subcomponent;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 24/11/15.
 */
@PerExecution @Subcomponent(modules = InteractorsModule.class)
public interface InteractorExecutionComponent extends InteractorsModuleProvider {
    void injectSaveUserInteractorExecution(InteractorExecution<ClientAuthData> interactorExecution);
    void injectConfigInteractorInteractorExecution(InteractorExecution<OrchextraUpdates> interactorExecution);
    void injectRegionsProviderInteractorExecution(InteractorExecution<List<OrchextraRegion>> interactorExecution);
    void injectBeaconEventsInteractorExecution(InteractorExecution<List<BasicAction>> interactorExecution);
    void injectGeofenceInteractorExecution(InteractorExecution<List<BasicAction>> interactorExecution);

}
