package com.gigigo.orchextra.domain.services.status;

import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;
import com.gigigo.orchextra.domain.services.DomainService;

public class ClearOrchextraCredentialsDomainService implements DomainService {

    private final AuthenticationDataProvider authenticationDataProvider;

    public ClearOrchextraCredentialsDomainService(AuthenticationDataProvider authenticationDataProvider) {
        this.authenticationDataProvider = authenticationDataProvider;
    }

    public void clearSdkCredentials() {
        authenticationDataProvider.clearAuthenticatedSdk();
        authenticationDataProvider.clearAuthenticatedUser();
    }
}
