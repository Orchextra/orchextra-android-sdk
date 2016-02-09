package gigigo.com.orchextra.data.datasources.db.config;

import android.content.Context;

import com.gigigo.gggjavalib.business.model.BusinessContentType;
import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.gggjavalib.general.utils.ConsistencyUtils;
import com.gigigo.orchextra.dataprovision.config.datasource.ConfigDBDataSource;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.config.strategy.ConfigInfoResult;

import java.util.List;

import gigigo.com.orchextra.data.datasources.db.NotFountRealmObjectException;
import gigigo.com.orchextra.data.datasources.db.RealmDefaultInstance;
import io.realm.Realm;
import io.realm.exceptions.RealmException;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 21/12/15.
 */
public class ConfigDBDataSourceImpl extends RealmDefaultInstance implements ConfigDBDataSource {

  private final Context context;
  private final ConfigInfoResultUpdater configInfoResultUpdater;
  private final ConfigInfoResultReader configInfoResultReader;

  public ConfigDBDataSourceImpl(Context context, ConfigInfoResultUpdater configInfoResultUpdater,
      ConfigInfoResultReader configInfoResultReader) {

    this.context = context;
    this.configInfoResultUpdater = configInfoResultUpdater;
    this.configInfoResultReader = configInfoResultReader;
  }

  public boolean saveConfigData(ConfigInfoResult configInfoResult){

    Realm realm = getRealmInstance(context);

    try {
      realm.beginTransaction();
      configInfoResultUpdater.updateConfigInfoV2(realm, configInfoResult);
      realm.commitTransaction();
    }catch (RealmException re){
      return false;
    }finally {
      if (realm != null) {
        realm.close();
      }
    }

    return true;
  }

  public BusinessObject<ConfigInfoResult> obtainConfigData(){
    Realm realm = getRealmInstance(context);

    try {
      ConfigInfoResult configInfoResult = configInfoResultReader.readConfigInfoV2(realm);
      return new BusinessObject<>(configInfoResult, BusinessError.createOKInstance());
    }catch (NotFountRealmObjectException | RealmException re ){
      return new BusinessObject(null, BusinessError.createKoInstance(BusinessContentType.NO_CONFIG_ERROR, re.getMessage()));
    }finally {
      if (realm != null) {
        realm.close();
      }
    }
  }

  public OrchextraRegion obtainBeaconByUuid(String uuid){
    //TODO Manage exceptions
    return configInfoResultReader.getBeaconByUuid(Realm.getDefaultInstance(), uuid);
  }

  @Override
  public BusinessObject<List<OrchextraGeofence>> obtainGeofences() {
    Realm realm = getRealmInstance(context);
    try {
      List<OrchextraGeofence> geofenceList = configInfoResultReader.getAllGeofences(realm);

      geofenceList = (List<OrchextraGeofence>) ConsistencyUtils.checkNotEmpty(geofenceList);

      return new BusinessObject<>(geofenceList, BusinessError.createOKInstance());

    } catch (NotFountRealmObjectException | RealmException | NullPointerException | IllegalArgumentException re) {
      return new BusinessObject<>(null, new BusinessError(BusinessError.EXCEPTION_BUSINESS_ERROR_CODE, re.getMessage()));

    } finally {
      if(realm != null) {
        realm.close();
      }
    }
  }

  public BusinessObject<OrchextraGeofence> obtainGeofenceById(String id){
    Realm realm = getRealmInstance(context);
    try {
      OrchextraGeofence geofence = configInfoResultReader.getGeofenceById(realm, id);
      return new BusinessObject<>(geofence, BusinessError.createOKInstance());

    } catch (NotFountRealmObjectException | RealmException re) {
      return new BusinessObject<>(null, new BusinessError(BusinessError.EXCEPTION_BUSINESS_ERROR_CODE, re.getMessage()));

    } finally {
      if (realm != null) {
        realm.close();
      }
    }
  }

}
