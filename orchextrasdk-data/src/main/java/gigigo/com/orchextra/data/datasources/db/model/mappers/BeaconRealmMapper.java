package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.gggjavalib.general.utils.DateUtils;
import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.ggglib.network.mappers.DateFormatConstants;
import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.ggglib.mappers.ModelToExternalClassMapper;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.ProximityPointType;
import gigigo.com.orchextra.data.datasources.db.model.BeaconRealm;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class BeaconRealmMapper implements Mapper<OrchextraRegion, BeaconRealm> {

  private final KeyWordRealmMapper keyWordRealmMapper;

  public BeaconRealmMapper(KeyWordRealmMapper keyWordRealmMapper) {
    this.keyWordRealmMapper = keyWordRealmMapper;
  }

  @Override public BeaconRealm modelToExternalClass(OrchextraRegion beacon) {
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
    if (beacon.getType() != null) {
      beaconRealm.setType(beacon.getType().getStringValue());
    }

    beaconRealm.setCreatedAt(
        DateUtils.dateToStringWithFormat(beacon.getCreatedAt(), DateFormatConstants.DATE_FORMAT));

    beaconRealm.setUpdatedAt(
        DateUtils.dateToStringWithFormat(beacon.getUpdatedAt(), DateFormatConstants.DATE_FORMAT));

    return beaconRealm;
  }

  @Override public OrchextraRegion externalClassToModel(BeaconRealm beaconRealm) {

    OrchextraRegion beacon = new OrchextraRegion(
        beaconRealm.getCode(),
        beaconRealm.getUuid(),
        beaconRealm.getMajor(),
        beaconRealm.getMinor(),
        beaconRealm.isActive());

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
