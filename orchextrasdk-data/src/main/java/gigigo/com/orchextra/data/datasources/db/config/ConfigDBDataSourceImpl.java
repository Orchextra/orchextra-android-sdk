package gigigo.com.orchextra.data.datasources.db.config;

import android.content.Context;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.dataprovision.config.datasource.ConfigDBDataSource;
import com.gigigo.orchextra.dataprovision.config.model.strategy.ConfigInfoResult;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;

import java.util.List;

import gigigo.com.orchextra.data.datasources.db.NotFountRealmObjectException;
import gigigo.com.orchextra.data.datasources.db.RealmDefaultInstance;
import io.realm.Realm;
import io.realm.exceptions.RealmException;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class ConfigDBDataSourceImpl implements ConfigDBDataSource {

  private final Context context;
  private final ConfigInfoResultUpdater configInfoResultUpdater;
  private final ConfigInfoResultReader configInfoResultReader;
  private final RealmDefaultInstance realmDefaultInstance;

  public ConfigDBDataSourceImpl(Context context, ConfigInfoResultUpdater configInfoResultUpdater,
      ConfigInfoResultReader configInfoResultReader, RealmDefaultInstance realmDefaultInstance) {

    this.context = context;
    this.configInfoResultUpdater = configInfoResultUpdater;
    this.configInfoResultReader = configInfoResultReader;
    this.realmDefaultInstance = realmDefaultInstance;
  }

  public OrchextraUpdates saveConfigData(ConfigInfoResult configInfoResult){

    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      realm.beginTransaction();
      OrchextraUpdates orchextraUpdates = configInfoResultUpdater.updateConfigInfoV2(realm, configInfoResult);
      return orchextraUpdates;
    }catch (Exception re){
      re.printStackTrace();
      return null;
    }finally {
      realm.commitTransaction();
      if (realm != null) {
        realm.close();
      }
    }
  }

  public BusinessObject<ConfigInfoResult> obtainConfigData(){
    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      ConfigInfoResult configInfoResult = configInfoResultReader.readConfigInfo(realm);
      return new BusinessObject<>(configInfoResult, BusinessError.createOKInstance());
    }catch (NotFountRealmObjectException | RealmException re ){
      return new BusinessObject(null, BusinessError.createKoInstance(re.getMessage()));
    }finally {
      if (realm != null) {
        realm.close();
      }
    }
  }

  @Override public BusinessObject<List<OrchextraRegion>> obtainRegionsForScan() {
    Realm realm = realmDefaultInstance.createRealmInstance(context);
    List<OrchextraRegion> regions = configInfoResultReader.getAllRegions(realm);
    if (realm != null) {
      realm.close();
    }
    return new BusinessObject<>(regions, BusinessError.createOKInstance());
  }

  @Override
  public BusinessObject<OrchextraGeofence> obtainGeofenceById(String geofenceId){

    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      OrchextraGeofence geofence = configInfoResultReader.getGeofenceById(realm, geofenceId);
      return new BusinessObject<>(geofence, BusinessError.createOKInstance());

    } catch (NotFountRealmObjectException | RealmException re) {
      return new BusinessObject(null, BusinessError.createKoInstance(re.getMessage()));

    } finally {
      if (realm != null) {
        realm.close();
      }
    }
  }

}
