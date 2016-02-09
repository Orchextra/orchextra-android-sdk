package com.gigigo.orchextra.domain.model.triggers.params;

import com.gigigo.orchextra.domain.model.StringValueEnum;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public enum AppRunningModeType implements StringValueEnum {
  BACKGROUND("background"),
  FOREGROUND("foreground");

  private final String type;

  AppRunningModeType(final String type) {
    this.type = type;
  }

  public String getStringValue() {
    return type;
  }
}
