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

  public int getValue() {
    return codeError;
  }

  public static OrchextraBusinessErrors getEnumTypeFromInt(int errorCode){
    for (OrchextraBusinessErrors error : OrchextraBusinessErrors.values()) {
      if (error.getValue() == errorCode) {
        return error;
      }
    }
    return NO_AUTH_EXPIRED;
  }
}
