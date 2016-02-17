package com.gigigo.orchextra.domain.interactors.beacons;

import com.gigigo.orchextra.domain.model.StringValueEnum;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 10/2/16.
 */
public enum BeaconBusinessErrorType implements StringValueEnum{
  NO_SUCH_REGION_IN_ENTER("NO_SUCH_REGION_IN_ENTER"),
  TRIGGERS_GENRATION_EXCEPTION("TRIGGERS_GENRATION_EXCEPTION"),
  ALREADY_IN_ENTER_REGION("ALREADY_IN_ENTER_REGION"),
  UNKNOWN_EVENT("UNKNOWN_EVENT");

  private final String type;

  BeaconBusinessErrorType(final String type) {
    this.type = type;
  }

  public String getStringValue() {
    return type;
  }
}
