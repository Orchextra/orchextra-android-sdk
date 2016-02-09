package gigigo.com.orchextra.data.datasources.api.interceptors;

import com.gigigo.orchextra.domain.model.entities.authentication.Session;

public class AuthenticationHeaderProvider {

    private final String tokenType;
    private final Session session;
    private String authorizationToken;

    public AuthenticationHeaderProvider(String tokenType, Session session) {
        this.tokenType = tokenType;
        this.session = session;
    }

    public String getAuthorizationToken() {
        return authorizationToken;
    }

    public void setAuthorizationToken(String value) {
        //TODO manage session in order to provide right data
        this.authorizationToken =  tokenType + " " + value;
    }
}
