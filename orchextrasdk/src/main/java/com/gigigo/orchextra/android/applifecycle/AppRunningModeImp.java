package com.gigigo.orchextra.android.applifecycle;

import com.gigigo.orchextra.domain.device.AppRunningMode;
import com.gigigo.orchextra.domain.device.LifeCycleAccessor;
import com.gigigo.orchextra.domain.entities.triggers.AppRunningModeType;

public class AppRunningModeImp implements AppRunningMode {

    private LifeCycleAccessor lifeCycleAccessor;

    @Override public void setOrchextraActivityLifecycle(LifeCycleAccessor lifeCycleAccessor) {
        this.lifeCycleAccessor = lifeCycleAccessor;
    }

    //region running Mode interface

    @Override public AppRunningModeType getRunningModeType() {

        if (lifeCycleAccessor.isInBackground()){
            return AppRunningModeType.BACKGROUND;
        }else{
            return AppRunningModeType.FOREGROUND;
        }
    }

    //endregion
}
