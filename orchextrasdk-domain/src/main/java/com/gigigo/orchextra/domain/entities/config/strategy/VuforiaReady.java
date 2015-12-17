package com.gigigo.orchextra.domain.entities.config.strategy;

import com.gigigo.orchextra.domain.entities.MethodSupported;
import com.gigigo.orchextra.domain.entities.Vuforia;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public interface VuforiaReady extends MethodSupported {
  Vuforia getVuforia();
}
