package gigigo.com.orchextra.data.datasources.db.config;


import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.entities.Vuforia;

import gigigo.com.orchextra.data.datasources.db.model.VuforiaRealm;

import io.realm.Realm;

public class ConfigVuforiaUpdater {

    private final Mapper<Vuforia, VuforiaRealm> vuforiaRealmMapper;

    public ConfigVuforiaUpdater(Mapper<Vuforia, VuforiaRealm> vuforiaRealmMapper) {
        this.vuforiaRealmMapper = vuforiaRealmMapper;
    }

    public boolean saveVuforia(Realm realm, Vuforia vuforia) {
        boolean hasChangedVuforia = false;

        if (vuforia != null) {
            VuforiaRealm vuforiaRealm = vuforiaRealmMapper.modelToExternalClass(vuforia);

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
