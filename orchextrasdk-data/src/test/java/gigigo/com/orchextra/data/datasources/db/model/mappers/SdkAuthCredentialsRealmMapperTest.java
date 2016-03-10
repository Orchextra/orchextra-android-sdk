package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.orchextra.domain.model.entities.credentials.SdkAuthCredentials;

import org.junit.Test;

import gigigo.com.orchextra.data.datasources.builders.SdkAuthCredentialsBuilder;
import gigigo.com.orchextra.data.datasources.db.model.SdkAuthCredentialsRealm;

import static org.junit.Assert.assertEquals;

public class SdkAuthCredentialsRealmMapperTest {

    @Test
    public void shouldMapModelToData() throws Exception {
        SdkAuthCredentials sdkAuthCredentials = SdkAuthCredentialsBuilder.Builder().build();

        SdkAuthCredentialsRealmMapper mapper = new SdkAuthCredentialsRealmMapper();
        SdkAuthCredentialsRealm sdkAuthCredentialsRealm = mapper.modelToExternalClass(
            sdkAuthCredentials);

        assertEquals(SdkAuthCredentialsBuilder.KEY, sdkAuthCredentialsRealm.getApiKey());
        assertEquals(SdkAuthCredentialsBuilder.SECRET, sdkAuthCredentialsRealm.getApiSecret());
    }

    @Test
    public void shouldMapDataToModel() throws Exception {
        SdkAuthCredentialsRealm sdkAuthCredentialsRealm = new SdkAuthCredentialsRealm();
        sdkAuthCredentialsRealm.setApiKey(SdkAuthCredentialsBuilder.KEY);
        sdkAuthCredentialsRealm.setApiSecret(SdkAuthCredentialsBuilder.SECRET);

        SdkAuthCredentialsRealmMapper mapper = new SdkAuthCredentialsRealmMapper();
        SdkAuthCredentials sdkAuthCredentials = mapper.externalClassToModel(sdkAuthCredentialsRealm);

        assertEquals(SdkAuthCredentialsBuilder.KEY, sdkAuthCredentials.getApiKey());
        assertEquals(SdkAuthCredentialsBuilder.SECRET, sdkAuthCredentials.getApiSecret());
    }
}