package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.orchextra.domain.model.entities.VuforiaCredentials;

import org.junit.Test;

import gigigo.com.orchextra.data.datasources.builders.VuforiaBuilder;
import gigigo.com.orchextra.data.datasources.db.model.VuforiaCredentialsRealm;

import static org.junit.Assert.assertEquals;

public class VuforiaAuthCredentialsRealmMapperTest {

    @Test
    public void shouldMapModelToData() throws Exception {
        VuforiaCredentials vuforiaCredentials = VuforiaBuilder.Builder().build();

        VuforiaCredentialsRealmMapper mapper = new VuforiaCredentialsRealmMapper();
        VuforiaCredentialsRealm vuforiaCredentialsRealm = mapper.modelToExternalClass(vuforiaCredentials);

        assertEquals(VuforiaBuilder.ACCESS_KEY, vuforiaCredentialsRealm.getClientAccessKey());
        assertEquals(VuforiaBuilder.SECRET_KEY, vuforiaCredentialsRealm.getClientSecretKey());
        assertEquals(VuforiaBuilder.LICENSE_KEY, vuforiaCredentialsRealm.getLicenseKey());
    }

    @Test
    public void shouldMapDataToModel() throws Exception {
        VuforiaCredentialsRealm vuforiaCredentialsRealm = new VuforiaCredentialsRealm();
        vuforiaCredentialsRealm.setClientAccessKey(VuforiaBuilder.ACCESS_KEY);
        vuforiaCredentialsRealm.setClientSecretKey(VuforiaBuilder.SECRET_KEY);
        vuforiaCredentialsRealm.setLicenseKey(VuforiaBuilder.LICENSE_KEY);

        VuforiaCredentialsRealmMapper mapper = new VuforiaCredentialsRealmMapper();
        VuforiaCredentials vuforiaCredentials = mapper.externalClassToModel(vuforiaCredentialsRealm);

        assertEquals(VuforiaBuilder.ACCESS_KEY, vuforiaCredentials.getClientAccessKey());
        assertEquals(VuforiaBuilder.SECRET_KEY, vuforiaCredentials.getClientSecretKey());
        assertEquals(VuforiaBuilder.LICENSE_KEY, vuforiaCredentials.getLicenseKey());
    }
}