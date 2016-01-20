package com.gigigo.orchextra.control.mapper;

import com.gigigo.orchextra.control.entities.ControlProximityPointType;
import com.gigigo.orchextra.domain.entities.ProximityPointType;

public class ControlProximityPointTypeMapper implements Mapper<ProximityPointType,ControlProximityPointType> {

    @Override
    public ControlProximityPointType modelToControl(ProximityPointType model) {
        return ControlProximityPointType.getProximityPointTypeValue(model.getStringValue());
    }

    @Override
    public ProximityPointType controlToModel(ControlProximityPointType control) {
        return ProximityPointType.getProximityPointTypeValue(control.getStringValue());
    }
}
