package com.gigigo.orchextra.control.controllers.proximity;

import com.gigigo.orchextra.control.InteractorResult;
import com.gigigo.orchextra.control.controllers.base.Controller;
import com.gigigo.orchextra.control.entities.ControlGeofence;
import com.gigigo.orchextra.control.entities.ControlPoint;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.control.mapper.ListMapper;
import com.gigigo.orchextra.control.mapper.MapperControlToModel;
import com.gigigo.orchextra.domain.entities.Geofence;
import com.gigigo.orchextra.domain.entities.Point;
import com.gigigo.orchextra.domain.entities.triggers.GeoPointEventType;
import com.gigigo.orchextra.domain.interactors.actions.GetActionInteractor;
import com.gigigo.orchextra.domain.interactors.base.InteractorError;
import com.gigigo.orchextra.domain.interactors.geofences.RetrieveGeofenceDistanceInteractor;
import com.gigigo.orchextra.domain.interactors.geofences.RetrieveGeofencesFromDatabaseInteractor;
import com.gigigo.orchextra.domain.interactors.geofences.errors.RetrieveProximityItemError;

import java.util.List;

import me.panavtec.threaddecoratedview.views.ThreadSpec;

public class ProximityItemController extends Controller<ProximityItemDelegate> {

    private final InteractorInvoker interactorInvoker;
    private final RetrieveGeofencesFromDatabaseInteractor retrieveGeofencesInteractor;
    private final GetActionInteractor getActionInteractor;
    private final ListMapper<Geofence, ControlGeofence> controlGeofenceListMapper;
    private final MapperControlToModel<Point, ControlPoint> controlPointMapper;
    private final RetrieveGeofenceDistanceInteractor retrieveGeofenceDistanceInteractor;

    public ProximityItemController(ThreadSpec mainThreadSpec, InteractorInvoker interactorInvoker,
                                   RetrieveGeofencesFromDatabaseInteractor retrieveGeofencesInteractor,
                                   GetActionInteractor getActionInteractor, ListMapper<Geofence, ControlGeofence> controlGeofenceListMapper,
                                   MapperControlToModel<Point, ControlPoint> controlPointMapper,
                                   RetrieveGeofenceDistanceInteractor retrieveGeofenceDistanceInteractor) {
        super(mainThreadSpec);
        this.interactorInvoker = interactorInvoker;
        this.retrieveGeofencesInteractor = retrieveGeofencesInteractor;
        this.getActionInteractor = getActionInteractor;
        this.controlGeofenceListMapper = controlGeofenceListMapper;
        this.controlPointMapper = controlPointMapper;
        this.retrieveGeofenceDistanceInteractor = retrieveGeofenceDistanceInteractor;
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
                        List<ControlGeofence> controlGeofences = controlGeofenceListMapper.modelToControl(result);
                        registerGeofences(controlGeofences);
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

    private void registerGeofences(List<ControlGeofence> geofenceList) {
        getDelegate().registerGeofences(geofenceList);
    }

    private void doConfigurationRequest() {
        //TODO Call configuration interactor
    }

//    public void processTriggers(List<ControlTriggerGeofence> triggers) {
//        for (ControlTriggerGeofence trigger : triggers) {
//            getActionInteractor.setActionCriteria(trigger);
//
//            new InteractorExecution<>(getActionInteractor)
//                    .result(new InteractorResult<BasicAction>() {
//                        @Override
//                        public void onResult(BasicAction result) {
//                            //TODO Manage Action
//                            //TODO Manage Errors
//                        }
//                    }).execute(interactorInvoker);
//        }
//    }

    public void processTriggers(List<String> triggeringGeofenceIds, ControlPoint triggeringControlPoint, GeoPointEventType geofenceTransition) {
        Point triggeringPoint = controlPointMapper.controlToModel(triggeringControlPoint);

        retrieveGeofenceDistanceInteractor.setTriggeringPoint(triggeringPoint);

        for (String triggeringGeofenceId : triggeringGeofenceIds) {
            retrieveGeofenceDistanceInteractor.setGeofenceId(triggeringGeofenceId);
        }


        new InteractorExecution<>(retrieveGeofenceDistanceInteractor);


    }
}
