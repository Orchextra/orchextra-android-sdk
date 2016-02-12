package com.gigigo.orchextra.domain.interactors.error;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/2/16.
 */
public enum OrchextraBusinessErrors {
  NO_AUTH_EXPIRED(401),
  NO_AUTH_CREDENTIALS(403);

  private final int codeError;

  OrchextraBusinessErrors(int codeError) {
    this.codeError = codeError;
  }

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
