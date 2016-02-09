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
}
