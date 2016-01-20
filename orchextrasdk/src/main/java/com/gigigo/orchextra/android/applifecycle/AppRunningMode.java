package com.gigigo.orchextra.android.applifecycle;

import com.gigigo.orchextra.domain.entities.triggers.AppRunningModeType;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 18/1/16.
 */
public interface AppRunningMode {
  AppRunningModeType getRunningModeType();
}
