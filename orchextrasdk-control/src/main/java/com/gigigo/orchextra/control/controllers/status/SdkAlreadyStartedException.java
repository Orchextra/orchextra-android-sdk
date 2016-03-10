package com.gigigo.orchextra.control.controllers.status;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 8/3/16.
 */
public class SdkAlreadyStartedException extends RuntimeException {
  public SdkAlreadyStartedException(String s) {
    super(s);
  }
}
