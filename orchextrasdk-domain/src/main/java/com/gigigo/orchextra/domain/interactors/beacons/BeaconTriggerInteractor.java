package com.gigigo.orchextra.domain.interactors.beacons;

import com.gigigo.orchextra.domain.abstractions.lifecycle.AppRunningMode;
import com.gigigo.orchextra.domain.dataprovider.ProximityLocalDataProvider;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.triggers.strategy.types.Trigger;
import java.util.ArrayList;
import java.util.List;

public class BeaconTriggerInteractor implements Interactor<InteractorResponse<List<Trigger>>> {

    private final AppRunningMode appRunningMode;

    private OrchextraRegion orchextraRegion;
    private List<OrchextraBeacon> orchextraBeacons;

    public BeaconTriggerInteractor(ProximityLocalDataProvider proximityLocalDataProvider,
        AppRunningMode appRunningMode) {
        this.appRunningMode = appRunningMode;
    }

    @Override
    public InteractorResponse<List<Trigger>> call() throws Exception {
        try {

            if (orchextraBeacons != null) {
                return createTriggersForBeacons(orchextraBeacons);
            } else {
                return createTriggersForRegions(orchextraRegion);
            }
        }catch (Exception e){
         return new InteractorResponse<>(new BeaconsInteractorError(BeaconBusinessErrorType.TRIGGERS_GENRATION_EXCEPTION));
        }
    }

    private InteractorResponse createTriggersForRegions(OrchextraRegion orchextraRegion) {

        List<Trigger> triggers = new ArrayList<>();
        triggers.add(Trigger.createBeaconRegionTrigger(appRunningMode.getRunningModeType(),
            orchextraRegion));

        return new InteractorResponse(triggers);
    }

    private InteractorResponse createTriggersForBeacons(List<OrchextraBeacon> orchextraBeacons) {

        List<Trigger> triggers = new ArrayList<>();

        for (OrchextraBeacon orchextraBeacon: orchextraBeacons){
            triggers.add(Trigger.createBeaconTrigger(appRunningMode.getRunningModeType(), orchextraBeacon));
        }

        return new InteractorResponse(triggers);
    }

    public void setOrchextraRegion(OrchextraRegion orchextraRegion) {
        this.orchextraRegion = orchextraRegion;
    }

    public void setOrchextraBeacons(List<OrchextraBeacon> orchextraBeacons) {
        this.orchextraBeacons = orchextraBeacons;
    }

}
