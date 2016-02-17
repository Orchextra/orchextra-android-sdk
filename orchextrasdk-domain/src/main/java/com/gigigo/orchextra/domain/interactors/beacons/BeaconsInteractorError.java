package com.gigigo.orchextra.domain.interactors.beacons;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.orchextra.domain.interactors.base.InteractorError;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 10/2/16.
 */
public class BeaconsInteractorError implements InteractorError {

  private BeaconBusinessErrorType beaconBusinessErrorType;

  public BeaconsInteractorError(BeaconBusinessErrorType beaconBusinessErrorType) {
    this.beaconBusinessErrorType = beaconBusinessErrorType;
  }

  public BeaconBusinessErrorType getBeaconBusinessErrorType() {
    return beaconBusinessErrorType;
  }

  public void setBeaconBusinessErrorType(BeaconBusinessErrorType beaconBusinessErrorType) {
    this.beaconBusinessErrorType = beaconBusinessErrorType;
  }

  @Override public BusinessError getError() {
    return BusinessError.createKoInstance("Beacon exception produced : " + beaconBusinessErrorType.getStringValue());
  }
}
