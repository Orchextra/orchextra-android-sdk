package com.gigigo.orchextra.domain.interactors.geofences.errors;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.orchextra.domain.interactors.base.InteractorError;

public class RetrieveProximityItemError implements InteractorError {

    private final BusinessError error;

    public RetrieveProximityItemError(BusinessError businessError) {
        this.error = businessError;
    }

    public BusinessError getError() {
        return error;
    }
}
