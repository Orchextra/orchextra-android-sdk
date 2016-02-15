package com.gigigo.orchextra.domain.services.auth;

import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 12/2/16.
 */
public interface AuthenticationService {

  InteractorResponse authenticate();

  InteractorResponse authenticateUserWithCrmId(String crmId);

}
