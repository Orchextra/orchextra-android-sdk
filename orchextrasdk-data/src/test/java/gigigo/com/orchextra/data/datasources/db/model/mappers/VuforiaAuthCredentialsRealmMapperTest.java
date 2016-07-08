package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.orchextra.domain.model.entities.VuforiaCredentials;

import org.junit.Test;

import gigigo.com.orchextra.data.datasources.builders.VuforiaBuilder;
import gigigo.com.orchextra.data.datasources.db.model.VuforiaRealm;

import static org.junit.Assert.assertEquals;

public class VuforiaAuthCredentialsRealmMapperTest {

    @Test
    public void shouldMapModelToData() throws Exception {
        VuforiaCredentials vuforiaCredentials = VuforiaBuilder.Builder().build();

        VuforiaRealmMapper mapper = new VuforiaRealmMapper();
        VuforiaRealm vuforiaRealm = mapper.modelToExternalClass(vuforiaCredentials);

        assertEquals(VuforiaBuilder.ACCESS_KEY, vuforiaRealm.getClientAccessKey());
        assertEquals(VuforiaBuilder.SECRET_KEY, vuforiaRealm.getClientSecretKey());
        assertEquals(VuforiaBuilder.LICENSE_KEY, vuforiaRealm.getLicenseKey());
    }

    @Test
    public void shouldMapDataToModel() throws Exception {
        VuforiaRealm vuforiaRealm = new VuforiaRealm();
        vuforiaRealm.setClientAccessKey(VuforiaBuilder.ACCESS_KEY);
        vuforiaRealm.setClientSecretKey(VuforiaBuilder.SECRET_KEY);
        vuforiaRealm.setLicenseKey(VuforiaBuilder.LICENSE_KEY);

        VuforiaRealmMapper mapper = new VuforiaRealmMapper();
        VuforiaCredentials vuforiaCredentials = mapper.externalClassToModel(vuforiaRealm);

        assertEquals(VuforiaBuilder.ACCESS_KEY, vuforiaCredentials.getClientAccessKey());
        assertEquals(VuforiaBuilder.SECRET_KEY, vuforiaCredentials.getClientSecretKey());
        assertEquals(VuforiaBuilder.LICENSE_KEY, vuforiaCredentials.getLicenseKey());
    }
}