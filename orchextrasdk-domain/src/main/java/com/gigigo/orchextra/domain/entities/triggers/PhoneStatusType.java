package com.gigigo.orchextra.domain.entities.triggers;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public enum PhoneStatusType implements StringValueEnum{
  BACKGROUND("background"),
  FOREGROUND("foreground");

  private final String type;

  PhoneStatusType(final String type) {
    this.type = type;
  }

  public String getStringValue() {
    return type;
  }
}
