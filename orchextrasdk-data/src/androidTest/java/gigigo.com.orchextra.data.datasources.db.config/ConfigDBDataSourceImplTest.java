package gigigo.com.orchextra.data.datasources.db.config;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.entities.Vuforia;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import gigigo.com.orchextra.data.datasources.db.RealmDefaultInstance;
import gigigo.com.orchextra.data.datasources.db.model.VuforiaRealm;
import gigigo.com.orchextra.data.datasources.db.model.mappers.VuforiaRealmMapper;
import io.realm.Realm;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class ConfigDBDataSourceImplTest {

    private Realm realm;
    private ConfigVuforiaUpdater updater;

    Mapper<Vuforia, VuforiaRealm> vuforiaMapper;

    @Before
    public void setUp() throws Exception {
        vuforiaMapper = Mockito.mock(VuforiaRealmMapper.class);

        initRealmInstance();

        updater = new ConfigVuforiaUpdater(vuforiaMapper);
    }

    private void initRealmInstance() {
        Context context = InstrumentationRegistry.getContext();
        RealmDefaultInstance realmDefaultInstance = new RealmDefaultInstance();
        realm = realmDefaultInstance.createRealmInstance(context);
    }

    @Test
    public void shouldBeEmptyImageReconitionDatabaseWhenOneItemIsAddedAndThenIsRemoved() throws Exception {
        Vuforia vuforia = new Vuforia();
        vuforia.setClientAccessKey("Key");
        vuforia.setClientSecretKey("Secret");
        vuforia.setLicenseKey("License");

        VuforiaRealm vuforiaRealm = new VuforiaRealm();
        vuforiaRealm.setClientAccessKey("Key");
        vuforiaRealm.setClientSecretKey("Secret");
        vuforiaRealm.setLicenseKey("License");

        when(vuforiaMapper.modelToExternalClass(vuforia)).thenReturn(vuforiaRealm);

        assertThat(realm.where(VuforiaRealm.class).count(), is(0L));

        realm.beginTransaction();
        updater.saveVuforia(realm, vuforia);
        realm.commitTransaction();

        assertThat(realm.where(VuforiaRealm.class).count(), is(1L));

        realm.beginTransaction();
        updater.removeVuforia(realm);
        realm.commitTransaction();

        assertThat(realm.where(VuforiaRealm.class).count(), is(0L));
    }

    @After
    public void tearDown() throws Exception {
        realm.deleteAll();
    }
}