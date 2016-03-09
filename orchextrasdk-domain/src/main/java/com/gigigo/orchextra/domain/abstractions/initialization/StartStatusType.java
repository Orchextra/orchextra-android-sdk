package com.gigigo.orchextra.domain.abstractions.initialization;

import com.gigigo.orchextra.domain.model.StringValueEnum;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/3/16.
 */
public enum StartStatusType implements StringValueEnum{
  UNKNOWN_START_STATUS("SDK is in a corrupted status"),
  SDK_WAS_ALREADY_STARTED_WITH_SAME_CREDENTIALS("SDK was already started with the same credentials"),
  SDK_WAS_ALREADY_STARTED_WITH_DIFERENT_CREDENTIALS("SDK already started with different credentials"),
  SDK_WAS_NOT_INITIALIZED("SDK was not initialized"),
  SDK_READY_FOR_START("SDK was not started, and now is ready for starting");

  private final String type;

  StartStatusType(final String type) {
    this.type = type;
  }

  public String getStringValue() {
    return type;
  }

}
