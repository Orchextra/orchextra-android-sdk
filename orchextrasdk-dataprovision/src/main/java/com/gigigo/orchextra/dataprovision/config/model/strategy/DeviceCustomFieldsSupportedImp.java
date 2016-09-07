package com.gigigo.orchextra.dataprovision.config.model.strategy;

import com.gigigo.orchextra.domain.model.entities.tags.DeviceCustomFields;

public class DeviceCustomFieldsSupportedImp implements DeviceCustomFieldsSupported {

    private final DeviceCustomFields deviceCustomFields;

    public DeviceCustomFieldsSupportedImp(DeviceCustomFields deviceCustomFields) {
        this.deviceCustomFields = deviceCustomFields;
    }

    public DeviceCustomFields getDeviceCustomFields() {
        return deviceCustomFields;
    }

    @Override
    public boolean isSupported() {
        return deviceCustomFields != null;
    }
}
