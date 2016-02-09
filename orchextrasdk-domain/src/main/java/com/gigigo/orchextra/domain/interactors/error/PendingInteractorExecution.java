package com.gigigo.orchextra.domain.interactors.error;

import com.gigigo.orchextra.domain.interactors.base.Interactor;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/2/16.
 */
public class PendingInteractorExecution {

  private Interactor interactor;
  private boolean authenticationInProcess = false;

  public void loadInteractor(Interactor interactor) {
    if (!authenticationInProcess){
      this.interactor = interactor;
      authenticationInProcess = true;
    }else{
      //TODO unable to auth, already in proccess athentication
      // maybe throw exception??
      throw new AuthenticationAlreadyInProccess("Already being authenticated");
    }
  }

  public boolean isAuthenticationInProcess() {
    return authenticationInProcess;
  }

  public void execute(){
    try {
      interactor.call();
    }catch (Exception e){
      //TODO Log
    }
    authenticationInProcess = false;
  }

}
