package com.gigigo.orchextra.domain.interactors.beacons;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.ProximityLocalDataProvider;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;

public class RegionCheckerInteractor implements Interactor<InteractorResponse<OrchextraRegion>> {

    private final ProximityLocalDataProvider proximityLocalDataProvider;

    private OrchextraRegion orchextraRegion;

    public RegionCheckerInteractor(ProximityLocalDataProvider proximityLocalDataProvider) {
        this.proximityLocalDataProvider = proximityLocalDataProvider;
    }

    @Override
    public InteractorResponse<OrchextraRegion> call() throws Exception {
        if (orchextraRegion.isEnter()) {
            return new InteractorResponse(storeRegion());
        } else {
            return new InteractorResponse(deleteStoredRegion());
        }
    }

    private InteractorResponse deleteStoredRegion() {
        BusinessObject<OrchextraRegion> bo = proximityLocalDataProvider.deleteRegion(orchextraRegion);
        if (!bo.isSuccess()){
            return new InteractorResponse(new BeaconsInteractorError(BeaconBusinessErrorType.NO_SUCH_REGION_IN_ENTER));
        }else{
            return new InteractorResponse(bo.getData());
        }
    }

    private InteractorResponse storeRegion() throws Exception {
        BusinessObject<OrchextraRegion> bo = proximityLocalDataProvider.obtainRegion(orchextraRegion);
        if (bo.isSuccess()){
            return new InteractorResponse(new BeaconsInteractorError(BeaconBusinessErrorType.ALREADY_IN_ENTER_REGION));
        }else{
            bo = proximityLocalDataProvider.storeRegion(orchextraRegion);
            return new InteractorResponse(bo.getData());
        }
    }

    public void setOrchextraRegion(OrchextraRegion orchextraRegion) {
        this.orchextraRegion = orchextraRegion;
    }
}
