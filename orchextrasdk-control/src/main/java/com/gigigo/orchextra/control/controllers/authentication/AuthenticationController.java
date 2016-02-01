package com.gigigo.orchextra.control.controllers.authentication;

import com.gigigo.orchextra.control.InteractorResult;
import com.gigigo.orchextra.control.controllers.base.Controller;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.domain.entities.ClientAuthData;
import com.gigigo.orchextra.domain.entities.SdkAuthCredentials;
import com.gigigo.orchextra.domain.interactors.authentication.AuthenticationInteractor;
import com.gigigo.orchextra.domain.interactors.authentication.errors.SdkAuthError;

import me.panavtec.threaddecoratedview.views.ThreadSpec;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/12/15.
 */
public class AuthenticationController extends Controller<AuthenticationDelegate> {

  private final InteractorInvoker interactorInvoker;
  private final AuthenticationInteractor authenticationInteractor;

  public AuthenticationController(InteractorInvoker interactorInvoker,
      AuthenticationInteractor authenticationInteractor, ThreadSpec mainThreadSpec) {
    super(mainThreadSpec);
    this.interactorInvoker = interactorInvoker;
    this.authenticationInteractor = authenticationInteractor;
  }

  @Override public void onDelegateAttached() {
    getDelegate().onControllerReady();
  }

  public void authenticate(String apiKey, String apiSecret) {

    authenticationInteractor.setSdkAuthCredentials(new SdkAuthCredentials(apiKey, apiSecret));

    new InteractorExecution<>(authenticationInteractor).result(new InteractorResult<ClientAuthData>() {
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
