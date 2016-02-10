package com.gigigo.orchextra.domain.interactors.error;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/2/16.
 */
public class AuthenticationAlreadyInProccess extends RuntimeException {

  public AuthenticationAlreadyInProccess(String s) {
    super(s);
  }
}
