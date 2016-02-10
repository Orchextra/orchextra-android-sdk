package com.gigigo.orchextra.dataprovision.proximity.datasource;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public interface BeaconsDBDataSource {

  BusinessObject<OrchextraRegion> obtainRegionEvent(OrchextraRegion orchextraRegion);

  BusinessObject<OrchextraRegion> storeRegionEvent(OrchextraRegion orchextraRegion);

  BusinessObject<OrchextraRegion> deleteRegionEvent(OrchextraRegion orchextraRegion);

  BusinessObject<OrchextraBeacon> storeBeaconEvent(OrchextraBeacon beacon);

  BusinessObject<OrchextraRegion> updateRegionWithActionId(OrchextraRegion orchextraRegion);

  boolean deleteAllBeaconsInListWithTimeStampt(List<OrchextraBeacon> beacons, int requestTime);

  boolean isBeaconEventStored(OrchextraBeacon beacon);
}
