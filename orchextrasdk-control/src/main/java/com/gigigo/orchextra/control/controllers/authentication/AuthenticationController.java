package com.gigigo.orchextra.control.controllers.authentication;

import com.gigigo.orchextra.control.InteractorResult;
import com.gigigo.orchextra.control.controllers.base.Controller;
import com.gigigo.orchextra.control.invoker.InteractorExecution;
import com.gigigo.orchextra.control.invoker.InteractorInvoker;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.error.AuthErrorHandler;
import com.gigigo.orchextra.domain.interactors.error.PendingInteractorExecution;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.Session;
import com.gigigo.orchextra.domain.model.entities.credentials.SdkAuthCredentials;
import com.gigigo.orchextra.domain.interactors.authentication.AuthenticationInteractor;
import com.gigigo.orchextra.domain.interactors.authentication.errors.SdkAuthError;

import me.panavtec.threaddecoratedview.views.ThreadSpec;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/12/15.
 */
public class AuthenticationController extends Controller<AuthenticationDelegate> implements
    AuthErrorHandler {

  private final InteractorInvoker interactorInvoker;
  private final AuthenticationInteractor authenticationInteractor;
  private final Session session;

  public AuthenticationController(InteractorInvoker interactorInvoker,
      AuthenticationInteractor authenticationInteractor, ThreadSpec mainThreadSpec, Session session) {
    super(mainThreadSpec);
    this.interactorInvoker = interactorInvoker;
    this.authenticationInteractor = authenticationInteractor;
    this.session = session;
  }

  @Override public void onDelegateAttached() {
    //getDelegate().onControllerReady();
  }

  public void authenticate(final PendingInteractorExecution pendingInteractorExecution) {
    authenticationInteractor.setSdkAuthCredentials(new SdkAuthCredentials(session.getApiKey(), session.getApiSecret()));

    new InteractorExecution<>(authenticationInteractor).result(new InteractorResult<ClientAuthData>() {
      @Override public void onResult(ClientAuthData result) {
        //getDelegate().authenticationSuccessful();
        try{
          pendingInteractorExecution.execute();
        }catch (Exception e){
          //TODO Inform about error, Log error
        }
      }
    }).error(SdkAuthError.class, new InteractorResult<SdkAuthError>() {
      @Override
      public void onResult(SdkAuthError result) {
        //getDelegate().authenticationError();
        //TODO Inform about error
      }
    }).execute(interactorInvoker);
  }

  @Override public void authenticateWhenError(PendingInteractorExecution pendingInteractorExecution) {
    authenticate(pendingInteractorExecution);
  }
}
