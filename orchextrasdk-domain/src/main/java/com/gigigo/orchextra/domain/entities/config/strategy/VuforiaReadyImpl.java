package com.gigigo.orchextra.domain.entities.config.strategy;

import com.gigigo.orchextra.domain.entities.Vuforia;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public class VuforiaReadyImpl implements VuforiaReady{

  private Vuforia vuforia;

  public VuforiaReadyImpl(Vuforia vuforia) {
    this.vuforia = vuforia;
  }

  @Override public Vuforia getVuforia() {
    return vuforia;
  }

  @Override public boolean isSupported() {
    return (vuforia==null)? false : true;
  }

}
