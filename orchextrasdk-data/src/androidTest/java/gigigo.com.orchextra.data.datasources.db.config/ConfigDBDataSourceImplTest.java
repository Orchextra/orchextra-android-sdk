package gigigo.com.orchextra.data.datasources.db.config;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.entities.VuforiaCredentials;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import gigigo.com.orchextra.data.datasources.db.RealmDefaultInstance;
import gigigo.com.orchextra.data.datasources.db.model.VuforiaCredentialsRealm;
import gigigo.com.orchextra.data.datasources.db.model.mappers.VuforiaCredentialsRealmMapper;
import io.realm.Realm;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class ConfigDBDataSourceImplTest {

    private Realm realm;
    private ConfigVuforiaCredentialsUpdater updater;

    Mapper<VuforiaCredentials, VuforiaCredentialsRealm> vuforiaMapper;

    @Before
    public void setUp() throws Exception {
        vuforiaMapper = Mockito.mock(VuforiaCredentialsRealmMapper.class);

        initRealmInstance();

        updater = new ConfigVuforiaCredentialsUpdater(vuforiaMapper);
    }

    private void initRealmInstance() {
        Context context = InstrumentationRegistry.getContext();
        RealmDefaultInstance realmDefaultInstance = new RealmDefaultInstance();
        realm = realmDefaultInstance.createRealmInstance(context);
    }

    @Test
    public void shouldBeEmptyImageReconitionDatabaseWhenOneItemIsAddedAndThenIsRemoved() throws Exception {
        VuforiaCredentials vuforiaCredentials = new VuforiaCredentials();
        vuforiaCredentials.setClientAccessKey("Key");
        vuforiaCredentials.setClientSecretKey("Secret");
        vuforiaCredentials.setLicenseKey("License");

        VuforiaCredentialsRealm vuforiaCredentialsRealm = new VuforiaCredentialsRealm();
        vuforiaCredentialsRealm.setClientAccessKey("Key");
        vuforiaCredentialsRealm.setClientSecretKey("Secret");
        vuforiaCredentialsRealm.setLicenseKey("License");

        when(vuforiaMapper.modelToExternalClass(vuforiaCredentials)).thenReturn(vuforiaCredentialsRealm);

        assertThat(realm.where(VuforiaCredentialsRealm.class).count(), is(0L));

        realm.beginTransaction();
        updater.saveVuforia(realm, vuforiaCredentials);
        realm.commitTransaction();

        assertThat(realm.where(VuforiaCredentialsRealm.class).count(), is(1L));

        realm.beginTransaction();
        updater.removeVuforia(realm);
        realm.commitTransaction();

        assertThat(realm.where(VuforiaCredentialsRealm.class).count(), is(0L));
    }

    @After
    public void tearDown() throws Exception {
        realm.beginTransaction();
        realm.delete(VuforiaCredentialsRealm.class);
        realm.commitTransaction();
    }
}