package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.entities.proximity.RegionEventType;
import gigigo.com.orchextra.data.datasources.db.model.BeaconRegionEventRealm;
import gigigo.com.orchextra.data.datasources.db.model.BeaconRegionRealm;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class BeaconRegionEventRealmMapper implements Mapper<OrchextraRegion, BeaconRegionEventRealm> {

  private final Mapper<OrchextraRegion, BeaconRegionRealm> beaconRegionRealmMapper;

  public BeaconRegionEventRealmMapper(Mapper<OrchextraRegion, BeaconRegionRealm> beaconRegionRealmMapper) {
    this.beaconRegionRealmMapper = beaconRegionRealmMapper;
  }

  @Override public BeaconRegionEventRealm modelToExternalClass(OrchextraRegion region) {
    BeaconRegionRealm beaconRegionRealm = beaconRegionRealmMapper.modelToExternalClass(region);
    BeaconRegionEventRealm regionEventRealm = new BeaconRegionEventRealm(beaconRegionRealm);
    return regionEventRealm;
  }

  @Override public OrchextraRegion externalClassToModel(BeaconRegionEventRealm regionEventRealm) {
    OrchextraRegion orchextraRegion = new OrchextraRegion(
        regionEventRealm.getCode(),
        regionEventRealm.getUuid(),
        regionEventRealm.getMajor(),
        regionEventRealm.getMinor(),
        regionEventRealm.isActive());

    orchextraRegion.setActionRelated(regionEventRealm.getActionRelated());
    orchextraRegion.setRegionEvent(RegionEventType.valueOf(RegionEventType.class, regionEventRealm.getEventType()));
    return orchextraRegion;
  }
}
