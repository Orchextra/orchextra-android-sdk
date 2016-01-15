package com.gigigo.orchextra.control.controllers.proximity;

import com.gigigo.orchextra.control.InteractorResult;
import com.gigigo.orchextra.control.controllers.base.Controller;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.domain.entities.Geofence;
import com.gigigo.orchextra.domain.entities.Point;
import com.gigigo.orchextra.domain.entities.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.entities.triggers.GeoPointEventType;
import com.gigigo.orchextra.domain.entities.triggers.GeofenceTrigger;
import com.gigigo.orchextra.domain.entities.triggers.PhoneStatusType;
import com.gigigo.orchextra.domain.entities.triggers.Trigger;
import com.gigigo.orchextra.domain.interactors.actions.GetActionInteractor;
import com.gigigo.orchextra.domain.interactors.base.InteractorError;
import com.gigigo.orchextra.domain.interactors.geofences.RetrieveGeofencesFromDatabaseInteractor;
import com.gigigo.orchextra.domain.interactors.geofences.errors.RetrieveProximityItemError;

import java.util.List;

import me.panavtec.threaddecoratedview.views.ThreadSpec;

public class ProximityItemController extends Controller<ProximityItemDelegate> {

    private final InteractorInvoker interactorInvoker;
    private final RetrieveGeofencesFromDatabaseInteractor retrieveGeofencesInteractor;
    private final GetActionInteractor getActionInteractor;

    public ProximityItemController(ThreadSpec mainThreadSpec, InteractorInvoker interactorInvoker,
                                   RetrieveGeofencesFromDatabaseInteractor retrieveGeofencesInteractor,
                                   GetActionInteractor getActionInteractor) {
        super(mainThreadSpec);
        this.interactorInvoker = interactorInvoker;
        this.retrieveGeofencesInteractor = retrieveGeofencesInteractor;
        this.getActionInteractor = getActionInteractor;
    }

    @Override
    public void onDelegateAttached() {
        getDelegate().onControllerReady();
    }

    public void retrieveGeofences() {
        new InteractorExecution<>(retrieveGeofencesInteractor)
                .result(new InteractorResult<List<Geofence>>() {
                    @Override
                    public void onResult(List<Geofence> result) {
                        registerGeofences(result);
                    }
                })
                .error(RetrieveProximityItemError.class, new InteractorResult<InteractorError>() {
                    @Override
                    public void onResult(InteractorError result) {
                        doConfigurationRequest();
                    }
                })
                .error(InteractorError.class, new InteractorResult<InteractorError>() {
                    @Override
                    public void onResult(InteractorError result) {
                        doConfigurationRequest();
                    }
                }).execute(interactorInvoker);
    }

    private void registerGeofences(List<Geofence> geofenceList) {
        getDelegate().registerGeofences(geofenceList);
    }

    private void doConfigurationRequest() {
        //TODO Call configuration interactor
    }

    public void processTriggers(List<String> triggerIds, Point point, PhoneStatusType phoneStatusType, float distance, GeoPointEventType geofencingTransition) {
        for (String triggerGeofenceId : triggerIds) {
            processTrigger(point, phoneStatusType, distance, geofencingTransition, triggerGeofenceId);
        }
    }

    public void processTrigger(Point point, PhoneStatusType phoneStatusType, float distance, GeoPointEventType geofencingTransition, String triggerGeofenceId) {
        GeofenceTrigger trigger = Trigger.createGeofenceTrigger(triggerGeofenceId, point, phoneStatusType, distance, geofencingTransition);
        getActionInteractor.setActionCriteria(trigger);

        new InteractorExecution<>(getActionInteractor)
                .result(new InteractorResult<BasicAction>() {
                    @Override
                    public void onResult(BasicAction result) {
                        //TODO Manage Action
                        //TODO Manage Errors
                    }
                }).execute(interactorInvoker);
    }
}
