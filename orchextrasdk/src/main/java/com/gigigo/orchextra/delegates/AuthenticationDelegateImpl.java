package com.gigigo.orchextra.delegates;

import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.orchextra.control.controllers.authentication.AuthenticationController;
import com.gigigo.orchextra.control.controllers.authentication.AuthenticationDelegate;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 3/12/15.
 */
public class AuthenticationDelegateImpl implements AuthenticationDelegate {

  //@Inject AuthenticationController authenticationController;

  private final AuthenticationController authenticationController;

  //private static AuthenticationDelegateImpl me;
  //private DelegateComponent delegateComponent;

  public AuthenticationDelegateImpl(AuthenticationController authenticationController) {
    this.authenticationController = authenticationController;
  }

  @Override public void init() {
    //delegateComponent = Orchextra.getInjector().injectAuthDelegate(this);
    authenticationController.attachDelegate(this);
  }

  @Override public void destroy() {
    authenticationController.detachDelegate();
    //delegateComponent = null;
    //me = null;
  }

  //@Override public void onControllerReady() {
  //
  //}

  @Override public void authenticationSuccessful() {
    destroy();

    //TODO Call pending operation if required
    //ConfigDelegateImp configDelegate = new ConfigDelegateImp();
    //configDelegate.init();
    //configDelegate.sendConfiguration();
  }

  @Override public void authenticationError() {
    //TODO log exception or inform client app using callback
    destroy();
  }

  @Override public void authenticationException() {
    //TODO log exception or inform client app using callback
    destroy();
  }

  //public static void authenticate (String apiKey, String apiSecret){
  //
  //  getInstance().authenticateTask(apiKey, apiSecret);
  //}

  private void authenticate(String apiKey, String apiSecret) {
    GGGLogImpl.log("Being Authenticated with credentials... " + apiKey + " // " + apiSecret);
    init();
    //authenticationController.authenticate(apiKey, apiSecret);
  }

  //private static AuthenticationDelegateImpl getInstance(){
  //  if ( me != null ){
  //    return me;
  //  }else{
  //    return new AuthenticationDelegateImpl();
  //  }
  //}

}
