package gigigo.com.orchextra.data.datasources.db.beacons;

import android.content.Context;
import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.dataprovision.proximity.datasource.BeaconsDBDataSource;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import gigigo.com.orchextra.data.datasources.db.RealmDefaultInstance;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class BeaconsDBDataSourceImpl extends RealmDefaultInstance implements BeaconsDBDataSource {

  private final Context context;
  private final BeaconEventsUpdater beaconEventsUpdater;
  private final BeaconEventsReader beaconEventsReader;

  public BeaconsDBDataSourceImpl(Context context, BeaconEventsUpdater beaconEventsUpdater,
      BeaconEventsReader beaconEventsReader) {

    this.context = context;
    this.beaconEventsUpdater = beaconEventsUpdater;
    this.beaconEventsReader = beaconEventsReader;
  }

  @Override
  public BusinessObject<OrchextraRegion> obtainRegionEvent(OrchextraRegion orchextraRegion) {
    try {
      OrchextraRegion region = beaconEventsReader.obtainRegionEvent(getRealmInstance(context), orchextraRegion);
      return new BusinessObject<>(region, BusinessError.createOKInstance());
    }catch (Exception e){
      return new BusinessObject<>(null, BusinessError.createKoInstance(e.getMessage()));
    }
  }

  @Override
  public BusinessObject<OrchextraRegion> storeRegionEvent(OrchextraRegion orchextraRegion) {
    try {
      OrchextraRegion region = beaconEventsUpdater.storeRegionEvent(getRealmInstance(context),
          orchextraRegion);
      return new BusinessObject<>(region, BusinessError.createOKInstance());
    }catch (Exception e){
      return new BusinessObject<>(null, BusinessError.createKoInstance(e.getMessage()));
    }
  }

  @Override
  public BusinessObject<OrchextraRegion> deleteRegionEvent(OrchextraRegion orchextraRegion) {
    try {
      OrchextraRegion region = beaconEventsUpdater.deleteRegionEvent(getRealmInstance(context),
          orchextraRegion);
      return new BusinessObject<>(region, BusinessError.createOKInstance());
    }catch (Exception e){
      return new BusinessObject<>(null, BusinessError.createKoInstance(e.getMessage()));
    }
  }

  @Override public BusinessObject<OrchextraBeacon> storeBeaconEvent(OrchextraBeacon beacon) {
    try {
      OrchextraBeacon storedBeacon = beaconEventsUpdater.storeBeaconEvent(getRealmInstance(context),
          beacon);
      return new BusinessObject<>(storedBeacon, BusinessError.createOKInstance());
    }catch (Exception e){
      return new BusinessObject<>(null, BusinessError.createKoInstance(e.getMessage()));
    }
  }

  /**
   * @param beacons
   * @param requestTime
   * @return true if empty false if there are still beacons stored after delete operation
   */
  @Override public boolean deleteAllBeaconsInListWithTimeStampt(List<OrchextraBeacon> beacons, int requestTime) {
      return beaconEventsUpdater.deleteAllBeaconsInListWithTimeStampt(getRealmInstance(context),
        beacons, requestTime);
  }

  @Override public boolean isBeaconEventStored(OrchextraBeacon beacon) {
      return beaconEventsReader.isBeaconEventStored(getRealmInstance(context), beacon);
  }

  @Override public BusinessObject<OrchextraRegion> updateRegionWithActionId(OrchextraRegion orchextraRegion) {
    try {
      OrchextraRegion orchextraRegionUpdated = beaconEventsUpdater.addActionToRegion(
          getRealmInstance(context), orchextraRegion);

      return new BusinessObject<>(orchextraRegionUpdated, BusinessError.createOKInstance());
    }catch (Exception e){
      return new BusinessObject<>(null,
          BusinessError.createKoInstance(e.getMessage()));
    }
  }
}
