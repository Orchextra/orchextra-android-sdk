package gigigo.com.orchextra.data.datasources.db.config;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.entities.Vuforia;
import gigigo.com.orchextra.data.datasources.db.model.VuforiaRealm;
import io.realm.Realm;
import io.realm.RealmResults;

public class ConfigVuforiaUpdater {

  private final Mapper<Vuforia, VuforiaRealm> vuforiaRealmMapper;

  public ConfigVuforiaUpdater(Mapper<Vuforia, VuforiaRealm> vuforiaRealmMapper) {
    this.vuforiaRealmMapper = vuforiaRealmMapper;
  }

  public Vuforia saveVuforia(Realm realm, Vuforia vuforia) {
    boolean hasChangedVuforia = false;

    if (vuforia != null) {
      hasChangedVuforia = checkIfChangedVuforia(realm, vuforia);
    } else {
      realm.clear(VuforiaRealm.class);
    }

    if (hasChangedVuforia) {
      return vuforia;
    } else {
      return null;
    }
  }

  private boolean checkIfChangedVuforia(Realm realm, Vuforia vuforia) {
    boolean hasChangedVuforia = false;

    VuforiaRealm vuforiaRealm = vuforiaRealmMapper.modelToExternalClass(vuforia);

    RealmResults<VuforiaRealm> savedVuforia = realm.where(VuforiaRealm.class).findAll();
    if (savedVuforia.size() > 0) {
      hasChangedVuforia = !checkVuforiaAreEquals(vuforiaRealm, savedVuforia.first());
    }

    if (hasChangedVuforia) {
      realm.clear(VuforiaRealm.class);
      realm.copyToRealm(vuforiaRealm);
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
