package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.entities.Vuforia;
import gigigo.com.orchextra.data.datasources.db.model.VuforiaRealm;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class VuforiaRealmMapper implements Mapper<Vuforia, VuforiaRealm> {

  @Override public VuforiaRealm modelToExternalClass(Vuforia vuforia) {
    VuforiaRealm vuforiaRealm = new VuforiaRealm();

    if (vuforia != null) {
      vuforiaRealm.setClientAccessKey(vuforia.getClientAccessKey());
      vuforiaRealm.setClientSecretKey(vuforia.getClientSecretKey());
      vuforiaRealm.setLicenseKey(vuforia.getLicenseKey());
      vuforiaRealm.setServerAccessKey(vuforia.getServerAccessKey());
      vuforiaRealm.setServerSecretKey(vuforia.getServerSecretKey());
    }

    return vuforiaRealm;
  }

  @Override public Vuforia externalClassToModel(VuforiaRealm vuforiaRealm) {
    Vuforia vuforia = new Vuforia();

    if (vuforiaRealm != null) {
      vuforia.setClientAccessKey(vuforiaRealm.getClientAccessKey());
      vuforia.setClientSecretKey(vuforiaRealm.getClientSecretKey());
      vuforia.setLicenseKey(vuforiaRealm.getLicenseKey());
      vuforia.setServerAccessKey(vuforiaRealm.getServerAccessKey());
      vuforia.setServerSecretKey(vuforiaRealm.getServerSecretKey());
    }

    return vuforia;
  }
}
