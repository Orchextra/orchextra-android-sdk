package com.gigigo.orchextra.domain.services.status;

import com.gigigo.orchextra.domain.dataprovider.AuthenticationDataProvider;
import com.gigigo.orchextra.domain.services.DomaninService;

public class ClearOrchextraCredentialsService implements DomaninService {

    private final AuthenticationDataProvider authenticationDataProvider;

    public ClearOrchextraCredentialsService(AuthenticationDataProvider authenticationDataProvider) {
        this.authenticationDataProvider = authenticationDataProvider;
    }

    public void clearSdkCredentials() {
        authenticationDataProvider.clearAuthenticatedSdk();
        authenticationDataProvider.clearAuthenticatedUser();
    }
}
