package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.triggers.params.BeaconDistanceType;
import gigigo.com.orchextra.data.datasources.db.model.BeaconEventRealm;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class BeaconEventRealmMapper implements Mapper<OrchextraBeacon, BeaconEventRealm> {

  @Override public OrchextraBeacon externalClassToModel(BeaconEventRealm data) {
    OrchextraBeacon orchextraBeacon =
        new OrchextraBeacon(data.getUuid(), data.getMayor(), data.getMinor(),
            BeaconDistanceType.getValueFromString(data.getBeaconDistance()));

    orchextraBeacon.setCode(data.getCode());

    return orchextraBeacon;
  }

  @Override public BeaconEventRealm modelToExternalClass(OrchextraBeacon orchextraBeacon) {
    BeaconEventRealm beaconEventRealm = new BeaconEventRealm();

    beaconEventRealm.setCode(orchextraBeacon.getCode());
    beaconEventRealm.setUuid(orchextraBeacon.getUuid());
    beaconEventRealm.setMayor(orchextraBeacon.getMayor());
    beaconEventRealm.setMinor(orchextraBeacon.getMinor());
    beaconEventRealm.setKeyForRealm(
        orchextraBeacon.getCode() + orchextraBeacon.getBeaconDistance().getStringValue());
    beaconEventRealm.setBeaconDistance(orchextraBeacon.getBeaconDistance().getStringValue());

    return beaconEventRealm;
  }
}
