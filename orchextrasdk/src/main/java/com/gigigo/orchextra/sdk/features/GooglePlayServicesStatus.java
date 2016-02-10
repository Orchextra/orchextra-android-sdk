package com.gigigo.orchextra.sdk.features;

import com.gigigo.orchextra.domain.model.StringValueEnum;
import com.google.android.gms.common.ConnectionResult;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 2/2/16.
 */
public enum GooglePlayServicesStatus implements StringValueEnum {
  SUCCESS("Google play services initialization Successful"),
  SERVICE_MISSING("Google play services missing"), //1
  SERVICE_VERSION_UPDATE_REQUIRED("Google play services update required"), //2
  SERVICE_UPDATING("Google play services updating try again later "), //18
  SERVICE_DISABLED("Google play services disabled"),  //3
  SERVICE_INVALID("Google play services :: service invalid"); //9

  private final String type;

  GooglePlayServicesStatus(final String type) {
    this.type = type;
  }

  public String getStringValue() {
    return type;
  }

  public static GooglePlayServicesStatus getGooglePlayServicesStatus(int googleStatus){
    switch (googleStatus){
      case ConnectionResult.SUCCESS:
        return SUCCESS;
      case ConnectionResult.SERVICE_MISSING:
        return SERVICE_MISSING;
      case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
        return SERVICE_VERSION_UPDATE_REQUIRED;
      case ConnectionResult.SERVICE_UPDATING:
        return SERVICE_UPDATING;
      case ConnectionResult.SERVICE_DISABLED:
        return SERVICE_DISABLED;
      case ConnectionResult.SERVICE_INVALID:
        return SERVICE_INVALID;
      default:
        return SUCCESS;
    }
  }
}
