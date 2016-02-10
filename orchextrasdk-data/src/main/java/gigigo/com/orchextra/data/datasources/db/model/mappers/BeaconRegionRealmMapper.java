package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.entities.proximity.RegionEventType;
import gigigo.com.orchextra.data.datasources.db.model.BeaconRegionRealm;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class BeaconRegionRealmMapper implements Mapper<OrchextraRegion, BeaconRegionRealm> {

  @Override public BeaconRegionRealm modelToExternalClass(OrchextraRegion region) {
    BeaconRegionRealm regionRealm = new BeaconRegionRealm();

    regionRealm.setCode(region.getCode());
    regionRealm.setUuid(region.getUuid());
    regionRealm.setMajor(region.getMajor());
    regionRealm.setMinor(region.getMinor());

    regionRealm.setActionRelated(region.getActionRelated());
    regionRealm.setEventType(region.getRegionEvent().getStringValue());

    return regionRealm;
  }

  @Override public OrchextraRegion externalClassToModel(BeaconRegionRealm regionRealm) {

    OrchextraRegion region = new OrchextraRegion(
        regionRealm.getCode(),
        regionRealm.getUuid(),
        regionRealm.getMajor(),
        regionRealm.getMinor(),
        regionRealm.isActive());

    region.setActionRelated(regionRealm.getActionRelated());
    region.setRegionEvent(RegionEventType.valueOf(RegionEventType.class, regionRealm.getEventType()));

    return region;
  }
}
