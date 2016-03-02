package com.gigigo.orchextra.domain.abstractions.lifecycle;

import com.gigigo.orchextra.domain.model.triggers.params.AppRunningModeType;

public interface AppRunningMode {

  AppRunningModeType getRunningModeType();

  void setOrchextraActivityLifecycle(LifeCycleAccessor lifeCycleAccessor);
}
