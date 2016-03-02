package com.gigigo.orchextra.dataprovision.config.model.strategy;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public class VuforiaReadyImpl implements VuforiaReady {

  private com.gigigo.orchextra.domain.model.entities.Vuforia vuforia;

  public VuforiaReadyImpl(com.gigigo.orchextra.domain.model.entities.Vuforia vuforia) {
    this.vuforia = vuforia;
  }

  @Override public com.gigigo.orchextra.domain.model.entities.Vuforia getVuforia() {
    return vuforia;
  }

  @Override public boolean isSupported() {
    return (vuforia == null) ? false : true;
  }
}
