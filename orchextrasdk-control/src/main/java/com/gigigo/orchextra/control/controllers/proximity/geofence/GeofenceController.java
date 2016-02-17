package com.gigigo.orchextra.control.controllers.proximity.geofence;

import com.gigigo.orchextra.control.InteractorResult;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import com.gigigo.orchextra.domain.interactors.base.InteractorError;
import com.gigigo.orchextra.domain.interactors.geofences.GeofenceInteractor;
import com.gigigo.orchextra.domain.interactors.geofences.errors.RetrieveGeofenceItemError;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;

import java.util.List;


public class GeofenceController {

    private final InteractorInvoker interactorInvoker;
    private final GeofenceInteractor geofenceInteractor;
    private final ActionDispatcher actionDispatcher;

    public GeofenceController(InteractorInvoker interactorInvoker,
        GeofenceInteractor geofenceInteractor,
        ActionDispatcher actionDispatcher) {
        this.interactorInvoker = interactorInvoker;
        this.geofenceInteractor = geofenceInteractor;
        this.actionDispatcher = actionDispatcher;
    }

    public void processTriggers(List<String> triggeringGeofenceIds, OrchextraPoint triggeringPoint,
                                final GeoPointEventType geofenceTransition) {

      geofenceInteractor.setGeofenceData(triggeringGeofenceIds, triggeringPoint, geofenceTransition);

        new InteractorExecution<>(geofenceInteractor)
                .result(new InteractorResult<List<BasicAction>>() {
                    @Override
                    public void onResult(List<BasicAction> actions) {
                        executeActions(actions);
                    }
                })
                .error(RetrieveGeofenceItemError.class, new InteractorResult<InteractorError>() {
                    @Override
                    public void onResult(InteractorError result) {
                        // TODO Do something with interactor error
                    }
                })
                .execute(interactorInvoker);
    }

    private void executeActions(List<BasicAction> actions) {
        for (BasicAction action : actions) {
            action.performAction(actionDispatcher);
        }
    }
}
