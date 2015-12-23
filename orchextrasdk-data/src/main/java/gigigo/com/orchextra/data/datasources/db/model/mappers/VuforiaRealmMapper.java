package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.orchextra.domain.entities.Vuforia;
import gigigo.com.orchextra.data.datasources.db.model.VuforiaRealm;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class VuforiaRealmMapper implements RealmMapper<Vuforia, VuforiaRealm> {

  @Override public VuforiaRealm modelToData(Vuforia vuforia) {
    VuforiaRealm vuforiaRealm = new VuforiaRealm();
    vuforiaRealm.setClientAccessKey(vuforia.getClientAccessKey());
    vuforiaRealm.setClientSecretKey(vuforia.getClientSecretKey());
    vuforiaRealm.setLicenseKey(vuforia.getLicenseKey());
    vuforiaRealm.setServerAccessKey(vuforia.getServerAccessKey());
    vuforiaRealm.setServerSecretKey(vuforia.getServerSecretKey());
    return vuforiaRealm;
  }

  @Override public Vuforia dataToModel(VuforiaRealm vuforiaRealm) {
    Vuforia vuforia = new Vuforia();
    vuforia.setClientAccessKey(vuforiaRealm.getClientAccessKey());
    vuforia.setClientSecretKey(vuforiaRealm.getClientSecretKey());
    vuforia.setLicenseKey(vuforiaRealm.getLicenseKey());
    vuforia.setServerAccessKey(vuforiaRealm.getServerAccessKey());
    vuforia.setServerSecretKey(vuforiaRealm.getServerSecretKey());
    return vuforia;
  }
}
