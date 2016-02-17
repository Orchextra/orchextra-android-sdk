package com.gigigo.orchextra.control.controllers.authentication;

import com.gigigo.orchextra.control.InteractorResult;
import com.gigigo.orchextra.control.controllers.base.Controller;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.domain.interactors.user.SaveUserInteractor;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.services.auth.errors.SdkAuthError;

import javax.inject.Provider;
import me.panavtec.threaddecoratedview.views.ThreadSpec;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/12/15.
 */
public class AuthenticationController extends Controller<AuthenticationDelegate>{

  private final InteractorInvoker interactorInvoker;
  private final Provider<InteractorExecution> interactorExecutionProvider;

  public AuthenticationController(InteractorInvoker interactorInvoker,
      Provider<InteractorExecution> interactorExecutionProvider, ThreadSpec mainThreadSpec) {
    super(mainThreadSpec);
    this.interactorInvoker = interactorInvoker;
    this.interactorExecutionProvider = interactorExecutionProvider;
  }

  @Override public void onDelegateAttached() {
    //getDelegate().onControllerReady();
  }

  public void saveUser(String crmId) {
    InteractorExecution interactorExecution = interactorExecutionProvider.get();
    SaveUserInteractor saveUserInteractor = (SaveUserInteractor) interactorExecution.getInteractor();
    saveUserInteractor.setCrm(crmId);

    interactorExecution.result(new InteractorResult<ClientAuthData>() {
      @Override public void onResult(ClientAuthData result) {
        getDelegate().authenticationSuccessful();
      }
    }).error(SdkAuthError.class, new InteractorResult<SdkAuthError>() {
      @Override
      public void onResult(SdkAuthError result) {
        getDelegate().authenticationError();
      }
    }).execute(interactorInvoker);
  }

}
