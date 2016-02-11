package com.gigigo.orchextra.dataprovision.config.model.strategy;

import com.gigigo.orchextra.domain.model.MethodSupported;
import com.gigigo.orchextra.domain.model.entities.Vuforia;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public interface VuforiaReady extends MethodSupported {
  Vuforia getVuforia();
}
