package com.gigigo.orchextra.di.modules.device;

import com.gigigo.orchextra.device.actions.ActionRecovery;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/2/16.
 */
public interface ActionsModuleProvider {
  ActionRecovery providesActionRecovery();
}
