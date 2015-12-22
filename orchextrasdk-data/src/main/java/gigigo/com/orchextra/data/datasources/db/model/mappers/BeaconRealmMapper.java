package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.gggjavalib.general.utils.DateUtils;
import com.gigigo.ggglib.network.mappers.DateFormatConstants;
import com.gigigo.orchextra.domain.entities.Beacon;
import com.gigigo.orchextra.domain.entities.ProximityPointType;
import gigigo.com.orchextra.data.datasources.db.model.BeaconRealm;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class BeaconRealmMapper implements RealmMapper<Beacon, BeaconRealm> {

  private final KeyWordRealmMapper keyWordRealmMapper;

  public BeaconRealmMapper(KeyWordRealmMapper keyWordRealmMapper) {
    this.keyWordRealmMapper = keyWordRealmMapper;
  }

  @Override public BeaconRealm modelToData(Beacon beacon) {
    BeaconRealm beaconRealm = new BeaconRealm();
    beaconRealm.setActive(beacon.isActive());
    beaconRealm.setMajor(beacon.getMajor());
    beaconRealm.setMinor(beacon.getMinor());
    beaconRealm.setUuid(beacon.getUuid());

    beaconRealm.setCode(beacon.getCode());
    beaconRealm.setId(beacon.getId());
    beaconRealm.setName(beacon.getName());
    beaconRealm.setNotifyOnEntry(beacon.isNotifyOnEntry());
    beaconRealm.setNotifyOnExit(beacon.isNotifyOnExit());
    beaconRealm.setStayTime(beacon.getStayTime());
    beaconRealm.setTags(keyWordRealmMapper.stringKeyWordsToRealmList(beacon.getTags()));
    beaconRealm.setType(beacon.getType().getStringValue());

    beaconRealm.setCreatedAt(
        DateUtils.dateToStringWithFormat(beacon.getCreatedAt(), DateFormatConstants.DATE_FORMAT));

    beaconRealm.setUpdatedAt(
        DateUtils.dateToStringWithFormat(beacon.getUpdatedAt(), DateFormatConstants.DATE_FORMAT));

    return beaconRealm;
  }

  @Override public Beacon dataToModel(BeaconRealm beaconRealm) {
    Beacon beacon = new Beacon();
    beacon.setActive(beaconRealm.isActive());
    beacon.setMajor(beaconRealm.getMajor());
    beacon.setMinor(beaconRealm.getMinor());
    beacon.setUuid(beaconRealm.getUuid());

    beacon.setCode(beaconRealm.getCode());
    beacon.setId(beaconRealm.getId());
    beacon.setName(beaconRealm.getName());
    beacon.setNotifyOnEntry(beaconRealm.getNotifyOnEntry());
    beacon.setNotifyOnExit(beaconRealm.getNotifyOnExit());
    beacon.setStayTime(beaconRealm.getStayTime());
    beacon.setTags(keyWordRealmMapper.realmKeyWordsToStringList(beaconRealm.getTags()));
    beacon.setType(ProximityPointType.getProximityPointTypeValue(beaconRealm.getType()));

    beacon.setCreatedAt(
        DateUtils.stringToDateWithFormat(beaconRealm.getCreatedAt(), DateFormatConstants.DATE_FORMAT));
    beacon.setUpdatedAt(
        DateUtils.stringToDateWithFormat(beaconRealm.getUpdatedAt(), DateFormatConstants.DATE_FORMAT));

    return beacon;
  }
}
