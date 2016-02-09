package com.gigigo.orchextra.domain.interactors.user;

import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;
import com.gigigo.orchextra.domain.model.GenderType;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public class LogOnUserInteractor implements Interactor<InteractorResponse<Boolean>> {

  private final AuthenticationDataProvider authenticationDataProvider;
  private Crm user;

  public LogOnUserInteractor(AuthenticationDataProvider authenticationDataProvider) {
    this.authenticationDataProvider = authenticationDataProvider;
  }

  public void setUserData(String id, GenderType gender, Date birthDate, List<String> keyWords){
    this.user = new Crm(id, gender, birthDate, keyWords);
  }

  @Override public InteractorResponse<Boolean> call() throws Exception {
    //ClientAuthCredentials credentials =
    //authenticationDataProvider.authenticateUser();
    return new InteractorResponse<>(new Boolean(true));
  }

}
