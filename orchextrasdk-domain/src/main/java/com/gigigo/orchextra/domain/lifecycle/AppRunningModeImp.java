package com.gigigo.orchextra.domain.lifecycle;

import com.gigigo.orchextra.domain.abstractions.lifecycle.AppRunningMode;
import com.gigigo.orchextra.domain.abstractions.lifecycle.LifeCycleAccessor;
import com.gigigo.orchextra.domain.model.triggers.params.AppRunningModeType;

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
