package com.gigigo.orchextra.domain.interactors.error;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/2/16.
 */
public enum OrchextraBusinessErrors {
  NO_AUTH_EXPIRED,
  NO_AUTH_CREDENTIALS;

  public static OrchextraBusinessErrors getEnumTypeFromInt(int errorCode){

    switch (errorCode){
      case 401:
        return NO_AUTH_EXPIRED;
      case 403:
        return NO_AUTH_CREDENTIALS;
      default:
        return NO_AUTH_EXPIRED;
    }
  }
}
