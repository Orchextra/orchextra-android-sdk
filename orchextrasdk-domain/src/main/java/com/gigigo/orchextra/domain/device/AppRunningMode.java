package com.gigigo.orchextra.domain.device;

import com.gigigo.orchextra.domain.entities.triggers.AppRunningModeType;

public interface AppRunningMode {
    AppRunningModeType getRunningModeType();

    void setOrchextraActivityLifecycle(LifeCycleAccessor lifeCycleAccessor);
}
