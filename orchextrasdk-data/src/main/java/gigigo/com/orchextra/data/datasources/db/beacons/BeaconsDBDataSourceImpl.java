package gigigo.com.orchextra.data.datasources.db.beacons;

import android.content.Context;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.dataprovision.proximity.datasource.BeaconsDBDataSource;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraBeacon;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;

import java.util.List;

import gigigo.com.orchextra.data.datasources.db.RealmDefaultInstance;
import io.realm.Realm;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class BeaconsDBDataSourceImpl implements BeaconsDBDataSource {

  private final Context context;
  private final BeaconEventsUpdater beaconEventsUpdater;
  private final BeaconEventsReader beaconEventsReader;
  private final RealmDefaultInstance realmDefaultInstance;

  public BeaconsDBDataSourceImpl(Context context, BeaconEventsUpdater beaconEventsUpdater,
      BeaconEventsReader beaconEventsReader, RealmDefaultInstance realmDefaultInstance) {

    this.context = context;
    this.beaconEventsUpdater = beaconEventsUpdater;
    this.beaconEventsReader = beaconEventsReader;
    this.realmDefaultInstance = realmDefaultInstance;
  }

  @Override
  public BusinessObject<OrchextraRegion> obtainRegionEvent(OrchextraRegion orchextraRegion) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);
    try {
      OrchextraRegion region = beaconEventsReader.obtainRegionEvent(realm, orchextraRegion);
      return new BusinessObject<>(region, BusinessError.createOKInstance());
    }catch (Exception e){
      return new BusinessObject<>(null, BusinessError.createKoInstance(e.getMessage()));
    } finally {
      if (realm != null) {
        realm.close();
      }
    }
  }

  @Override
  public BusinessObject<OrchextraRegion> storeRegionEvent(OrchextraRegion orchextraRegion) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);
    try {
      OrchextraRegion region = beaconEventsUpdater.storeRegionEvent(realm, orchextraRegion);
      return new BusinessObject<>(region, BusinessError.createOKInstance());
    }catch (Exception e){
      return new BusinessObject<>(null, BusinessError.createKoInstance(e.getMessage()));
    } finally {
      if (realm != null) {
        realm.close();
      }
    }
  }

  @Override
  public BusinessObject<OrchextraRegion> deleteRegionEvent(OrchextraRegion orchextraRegion) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);
    try {
      OrchextraRegion region = beaconEventsUpdater.deleteRegionEvent(realm, orchextraRegion);
      return new BusinessObject<>(region, BusinessError.createOKInstance());
    }catch (Exception e){
      return new BusinessObject<>(null, BusinessError.createKoInstance(e.getMessage()));
    } finally {
      if (realm != null) {
        realm.close();
      }
    }
  }

  @Override public BusinessObject<OrchextraBeacon> storeBeaconEvent(OrchextraBeacon beacon) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);
    try {
      OrchextraBeacon storedBeacon = beaconEventsUpdater.storeBeaconEvent(realm, beacon);
      return new BusinessObject<>(storedBeacon, BusinessError.createOKInstance());
    }catch (Exception e){
      return new BusinessObject<>(null, BusinessError.createKoInstance(e.getMessage()));
    } finally {
      if (realm != null) {
        realm.close();
      }
    }
  }

  /**
   * @param beacons
   * @param requestTime
   * @return true if empty false if there are still beacons stored after delete operation
   */
  @Override public boolean deleteAllBeaconsInListWithTimeStampt(List<OrchextraBeacon> beacons, int requestTime) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);
    boolean allBeaconsInListWithTimeStampt = beaconEventsUpdater.deleteAllBeaconsInListWithTimeStampt(realm, beacons, requestTime);
    if (realm != null) {
      realm.close();
    }
    return allBeaconsInListWithTimeStampt;
  }

  @Override public boolean isBeaconEventStored(OrchextraBeacon beacon) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);
    boolean beaconEventStored = beaconEventsReader.isBeaconEventStored(realm, beacon);
    if (realm != null) {
      realm.close();
    }
    return beaconEventStored;
  }

  @Override public BusinessObject<OrchextraRegion> updateRegionWithActionId(OrchextraRegion orchextraRegion) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);
    try {
      OrchextraRegion orchextraRegionUpdated = beaconEventsUpdater.addActionToRegion(realm, orchextraRegion);

      return new BusinessObject<>(orchextraRegionUpdated, BusinessError.createOKInstance());
    }catch (Exception e){
      return new BusinessObject<>(null,
          BusinessError.createKoInstance(e.getMessage()));
    } finally {
      if (realm != null) {
        realm.close();
      }
    }
  }
}
