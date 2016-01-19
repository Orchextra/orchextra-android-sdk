package com.gigigo.orchextra.android.applifecycle;

import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.domain.device.DeviceRunningModeType;
import com.gigigo.orchextra.domain.entities.triggers.AppRunningModeType;

public class DeviceRunningModeTypeImp implements DeviceRunningModeType {

    @Override
    public AppRunningModeType getAppRunningModeType() {
        return Orchextra.getOchextraLifeCycle().getRunningModeType();
    }
}
