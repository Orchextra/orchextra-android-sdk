package com.gigigo.orchextra.delegates;

import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.ggglogger.LogLevel;
import com.gigigo.orchextra.control.controllers.authentication.AuthenticationController;
import com.gigigo.orchextra.control.controllers.authentication.AuthenticationDelegate;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/12/15.
 */
public class AuthenticationDelegateImpl implements AuthenticationDelegate {

  private final AuthenticationController authenticationController;

  public AuthenticationDelegateImpl(AuthenticationController authenticationController) {
    this.authenticationController = authenticationController;
  }

  @Override public void init() {
    authenticationController.attachDelegate(this);
  }

  @Override public void destroy() {
    authenticationController.detachDelegate();
  }

  @Override public void authenticationSuccessful() {
    destroy();
  }

  @Override public void authenticationError() {
    GGGLogImpl.log("Authentication was not successful", LogLevel.ERROR);
    destroy();
  }

  @Override public void authenticationException() {
    GGGLogImpl.log("Authentication was not successful", LogLevel.ERROR);
    destroy();
  }

  public void authenticate(String apiKey, String apiSecret) {
    GGGLogImpl.log("Being Authenticated with credentials... " + apiKey + " // " + apiSecret);
    init();
    //TODO call this method for SDK start
  }

  public void saveUser(String crmId) {
    GGGLogImpl.log("Being Authenticated with client... " + crmId);
    init();
    authenticationController.saveUser(crmId);
  }

}
