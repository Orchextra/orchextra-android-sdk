package com.gigigo.orchextra.delegates;

import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.control.controllers.authentication.AuthenticationController;
import com.gigigo.orchextra.control.controllers.authentication.AuthenticationDelegate;
import com.gigigo.orchextra.di.components.DelegateComponent;

import javax.inject.Inject;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/12/15.
 */
public class AuthenticationDelegateImpl implements AuthenticationDelegate {

  @Inject AuthenticationController authenticationController;

  private static AuthenticationDelegateImpl me;
  private DelegateComponent delegateComponent;

  private AuthenticationDelegateImpl() {
    init();
  }

  @Override public void init() {
    delegateComponent = Orchextra.getInjector().injectAuthDelegate(this);
    authenticationController.attachDelegate(this);
  }

  @Override public void destroy() {
    authenticationController.detachDelegate();
    delegateComponent = null;
    me = null;
  }

  @Override public void onControllerReady() {}

  @Override public void authenticationSuccessful() {
    //TODO Move this to Orchextra class as singleton
    ConfigDelegateImp configDelegate = new ConfigDelegateImp();
    configDelegate.init();
    configDelegate.sendConfiguration();
  }

  @Override public void authenticationError() {}

  @Override public void authenticationException() {}

  public static void authenticate (String apiKey, String apiSecret){
    getInstance().authenticateTask(apiKey, apiSecret);
  }

  private void authenticateTask(String apiKey, String apiSecret) {
    GGGLogImpl.log("Being Authenticated with credentials... " + apiKey + " // " + apiSecret);
    authenticationController.authenticate(apiKey, apiSecret);
  }

  private static AuthenticationDelegateImpl getInstance(){
    if ( me != null ){
      return me;
    }else{
      return new AuthenticationDelegateImpl();
    }
  }

}
