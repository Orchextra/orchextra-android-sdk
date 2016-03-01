package com.gigigo.orchextra.di.modules.device;

import com.gigigo.orchextra.device.actions.ActionRecovery;
import com.google.gson.Gson;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/2/16.
 */
public interface ActionsModuleProvider {
  ActionRecovery providesActionRecovery();
  Gson gson();
}
