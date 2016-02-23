package com.gigigo.orchextra.domain.model;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 16/12/15.
 */
public enum GenderType {
  MALE("m"),
  FEMALE("f"),
  ND("n");

  private final String text;

  GenderType(final String text) {
    this.text = text;
  }

  public String getStringValue() {
    return text;
  }

  public static GenderType getTypeFromString(String gender) {
    for(GenderType genderType:GenderType.values()){
      if (genderType.getStringValue().equals(gender)){
        return genderType;
      }
    }
    return ND;
  }
}
