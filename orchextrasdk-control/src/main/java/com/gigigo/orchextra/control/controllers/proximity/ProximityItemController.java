package com.gigigo.orchextra.control.controllers.proximity;

import com.gigigo.orchextra.control.InteractorResult;
import com.gigigo.orchextra.control.controllers.base.Controller;
import com.gigigo.orchextra.control.entities.GeofenceControl;
import com.gigigo.orchextra.control.entities.mappers.ListMapper;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.domain.entities.Geofence;
import com.gigigo.orchextra.domain.interactors.base.InteractorError;
import com.gigigo.orchextra.domain.interactors.geofences.RetrieveGeofencesFromDatabaseInteractor;
import com.gigigo.orchextra.domain.interactors.geofences.errors.RetrieveProximityItemError;

import java.util.List;

import me.panavtec.threaddecoratedview.views.ThreadSpec;

public class ProximityItemController extends Controller<ProximityItemDelegate> {

    private final InteractorInvoker interactorInvoker;
    private final RetrieveGeofencesFromDatabaseInteractor retrieveGeofencesInteractor;
    private final ListMapper<Geofence, GeofenceControl> geofenceControlListMapper;

    public ProximityItemController(ThreadSpec mainThreadSpec, InteractorInvoker interactorInvoker,
                                   RetrieveGeofencesFromDatabaseInteractor retrieveGeofencesInteractor,
                                   ListMapper<Geofence, GeofenceControl> geofenceControlListMapper) {
        super(mainThreadSpec);
        this.interactorInvoker = interactorInvoker;
        this.retrieveGeofencesInteractor = retrieveGeofencesInteractor;
        this.geofenceControlListMapper = geofenceControlListMapper;
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
                        List<GeofenceControl> geofenceControlList = geofenceControlListMapper.modelToData(result);
                        registerGeofences(geofenceControlList);
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

    private void registerGeofences(List<GeofenceControl> geofenceControlList) {
        getDelegate().registerGeofences(geofenceControlList);
    }

    private void doConfigurationRequest() {
        //TODO Call configuration interactor
    }
}
