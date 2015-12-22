package com.gigigo.orchextra.domain.entities.actions;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 18/12/15.
 */
public enum ActionType {
  BROWSER("browser"),
  WEBVIEW("webview"),
  CUSTOM_SCHEME("custom_scheme"),
  SCAN("scan"),
  VUFORIA_SCAN("scan_vuforia"),
  NOTIFICATION("notification"),
  NOT_DEFINED("do_nothing");

  private final String action;

  ActionType(final String text) {
    this.action = text;
  }

  public String getStringValue() {
    return action;
  }

  public static ActionType getActionTypeValue(String type) {

    if (type == null) {
      return NOT_DEFINED;
    } else {
      if (type.equals(BROWSER.getStringValue())) {
        return BROWSER;
      } else if (type.equals(WEBVIEW.getStringValue())) {
        return WEBVIEW;
      } else if (type.equals(CUSTOM_SCHEME.getStringValue())) {
        return CUSTOM_SCHEME;
      } else if (type.equals(SCAN.getStringValue())) {
        return SCAN;
      } else if (type.equals(VUFORIA_SCAN.getStringValue())) {
        return VUFORIA_SCAN;
      } else if (type.equals(NOTIFICATION.getStringValue())) {
        return NOTIFICATION;
      } else {
        return NOT_DEFINED;
      }
    }
  }
}
