package com.gigigo.orchextra.domain.services.auth;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 12/2/16.
 */
public interface AuthenticationService {

  InteractorResponse authenticate();

  InteractorResponse authenticateUserWithCrmId(String crmId);

  BusinessObject<Crm> saveUser(Crm crm);
}
