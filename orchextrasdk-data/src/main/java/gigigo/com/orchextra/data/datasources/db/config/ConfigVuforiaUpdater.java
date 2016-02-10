package gigigo.com.orchextra.data.datasources.db.config;

import com.gigigo.orchextra.domain.entities.Vuforia;

import gigigo.com.orchextra.data.datasources.db.model.VuforiaRealm;
import gigigo.com.orchextra.data.datasources.db.model.mappers.RealmMapper;
import io.realm.Realm;

public class ConfigVuforiaUpdater {

    private final RealmMapper<Vuforia, VuforiaRealm> vuforiaRealmMapper;

    public ConfigVuforiaUpdater(RealmMapper<Vuforia, VuforiaRealm> vuforiaRealmMapper) {
        this.vuforiaRealmMapper = vuforiaRealmMapper;
    }

    public boolean saveVuforia(Realm realm, Vuforia vuforia) {
        boolean hasChangedVuforia = false;

        if (vuforia != null){
            VuforiaRealm vuforiaRealm = vuforiaRealmMapper.modelToData(vuforia);

            VuforiaRealm olderVuforia = realm.where(VuforiaRealm.class).findFirst();

            hasChangedVuforia = !checkVuforiaAreEquals(vuforiaRealm, olderVuforia);

            if (hasChangedVuforia) {
                realm.copyToRealm(vuforiaRealm);
            }
        } else {
            realm.clear(VuforiaRealm.class);
        }
        return hasChangedVuforia;
    }

    private boolean checkVuforiaAreEquals(VuforiaRealm vuforiaRealm, VuforiaRealm oldVuforia) {
        if (oldVuforia == null) {
            return false;
        }
        return vuforiaRealm.getClientAccessKey().equals(oldVuforia.getClientAccessKey()) &&
                vuforiaRealm.getClientSecretKey().equals(oldVuforia.getClientSecretKey()) &&
                vuforiaRealm.getLicenseKey().equals(oldVuforia.getLicenseKey()) &&
                vuforiaRealm.getServerAccessKey().equals(oldVuforia.getServerAccessKey()) &&
                vuforiaRealm.getServerSecretKey().equals(oldVuforia.getServerSecretKey());
    }
}
