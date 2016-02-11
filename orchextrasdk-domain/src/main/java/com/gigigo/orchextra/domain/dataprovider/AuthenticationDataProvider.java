package com.gigigo.orchextra.domain.dataprovider;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;
import com.gigigo.orchextra.domain.model.entities.authentication.SdkAuthData;
import com.gigigo.orchextra.domain.model.entities.credentials.Credentials;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/12/15.
 */
public interface AuthenticationDataProvider {

  BusinessObject<SdkAuthData> authenticateSdk(Credentials credentials);

  BusinessObject<ClientAuthData> authenticateUser(Credentials credentials, String crmId);

  BusinessObject<Crm> retrieveCrm();
}
