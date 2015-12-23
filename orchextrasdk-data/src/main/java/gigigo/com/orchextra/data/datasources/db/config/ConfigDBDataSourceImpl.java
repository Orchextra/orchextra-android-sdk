package gigigo.com.orchextra.data.datasources.db.config;

import android.content.Context;
import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.dataprovision.config.datasource.ConfigDBDataSource;
import com.gigigo.orchextra.domain.entities.Beacon;
import com.gigigo.orchextra.domain.entities.Geofence;
import com.gigigo.orchextra.domain.entities.Theme;
import com.gigigo.orchextra.domain.entities.Vuforia;
import com.gigigo.orchextra.domain.entities.config.strategy.ConfigInfoResult;
import gigigo.com.orchextra.data.datasources.db.NotFountRealmObjectException;
import gigigo.com.orchextra.data.datasources.db.model.GeofenceRealm;
import gigigo.com.orchextra.data.datasources.db.model.ThemeRealm;
import gigigo.com.orchextra.data.datasources.db.model.VuforiaRealm;
import gigigo.com.orchextra.data.datasources.db.model.mappers.RealmMapper;
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

  public ConfigDBDataSourceImpl(Context context, ConfigInfoResultUpdater configInfoResultUpdater,
      ConfigInfoResultReader configInfoResultReader) {

    this.context = context;
    this.configInfoResultUpdater = configInfoResultUpdater;
    this.configInfoResultReader = configInfoResultReader;
  }

  public boolean saveConfigData(ConfigInfoResult configInfoResult){

    Realm realm = Realm.getDefaultInstance();

    try {
      realm.beginTransaction();
      configInfoResultUpdater.updateConfigInfoV2(realm, configInfoResult);
      realm.commitTransaction();
    }catch (RealmException re){
      return false;
    }finally {
      realm.close();
    }

    return true;
  }

  public BusinessObject<ConfigInfoResult> obtainConfigData(){

    Realm realm = Realm.getDefaultInstance();
    ConfigInfoResult configInfoResult;

    try {
      realm.beginTransaction();
      configInfoResult = configInfoResultReader.readConfigInfoV2(realm);
      realm.commitTransaction();
    }catch (NotFountRealmObjectException | RealmException re ){
      //throw businessException for get config from network again
      return new BusinessObject(null, new BusinessError(BusinessError.EXCEPTION_BUSINESS_ERROR_CODE, re.getMessage()));
    }finally {
      realm.close();
    }

    return new BusinessObject<>(configInfoResult, BusinessError.createOKInstance());
  }

  public Beacon obtainBeaconByUuid(String uuid){
    //TODO Manage exceptions
    return configInfoResultReader.getBeaconByUuid(Realm.getDefaultInstance(), uuid);
  }

  public Geofence obtainGeofenceById(String id){
    //TODO Manage exceptions
    return configInfoResultReader.getGeofenceById(Realm.getDefaultInstance(), id);
  }

}
