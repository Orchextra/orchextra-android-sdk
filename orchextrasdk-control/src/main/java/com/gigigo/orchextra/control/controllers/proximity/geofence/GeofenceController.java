package com.gigigo.orchextra.control.controllers.proximity.geofence;

import com.gigigo.orchextra.control.InteractorResult;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.domain.abstractions.error.ErrorLogger;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import com.gigigo.orchextra.domain.interactors.geofences.GeofenceInteractor;
import com.gigigo.orchextra.domain.interactors.geofences.errors.RetrieveGeofenceItemError;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.triggers.params.GeoPointEventType;

import java.util.List;

import javax.inject.Provider;
import me.panavtec.threaddecoratedview.views.ThreadSpec;

public class GeofenceController {

    private final InteractorInvoker interactorInvoker;
    private final Provider<InteractorExecution> interactorExecutionProvider;
    private final ActionDispatcher actionDispatcher;
    private final ErrorLogger errorLogger;
    private final ThreadSpec mainThreadSpec;

    public GeofenceController(InteractorInvoker interactorInvoker,
        Provider<InteractorExecution> interactorExecutionProvider,
        ActionDispatcher actionDispatcher, ErrorLogger errorLogger, ThreadSpec mainThreadSpec) {
        this.interactorInvoker = interactorInvoker;
        this.interactorExecutionProvider = interactorExecutionProvider;
        this.actionDispatcher = actionDispatcher;
      this.errorLogger = errorLogger;
      this.mainThreadSpec = mainThreadSpec;
    }

    public void processTriggers(List<String> triggeringGeofenceIds, GeoPointEventType geofenceTransition) {

      InteractorExecution interactorExecution = interactorExecutionProvider.get();
      GeofenceInteractor geofenceInteractor = (GeofenceInteractor) interactorExecution.getInteractor();
      geofenceInteractor.setGeofenceData(triggeringGeofenceIds, geofenceTransition);

      interactorExecution.result(new InteractorResult<List<BasicAction>>() {
                  @Override public void onResult(List<BasicAction> actions) {
                    executeActions(actions);
                  }
                })
                .error(RetrieveGeofenceItemError.class, new InteractorResult<RetrieveGeofenceItemError>() {
                    @Override
                    public void onResult(RetrieveGeofenceItemError result) {
                        errorLogger.log(result.getError());
                    }
                })
                .execute(interactorInvoker);
    }

    private void executeActions(List<BasicAction> actions) {

        for (final BasicAction action : actions) {
          mainThreadSpec.execute(new Runnable() {
            @Override public void run() {
              action.performAction(actionDispatcher);
            }
          });
        }
    }
}
