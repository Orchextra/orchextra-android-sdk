/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gigigo.com.orchextra.data.datasources.db.config;

import android.content.Context;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.orchextra.dataprovision.config.datasource.TriggersConfigurationDBDataSource;
import com.gigigo.orchextra.dataprovision.config.model.strategy.ConfigurationInfoResult;
import com.gigigo.orchextra.domain.model.entities.geofences.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;

import java.util.List;

import gigigo.com.orchextra.data.datasources.db.NotFountRealmObjectException;
import gigigo.com.orchextra.data.datasources.db.RealmDefaultInstance;
import io.realm.Realm;
import io.realm.exceptions.RealmException;


public class TriggersConfigurationDBDataSourceImpl implements TriggersConfigurationDBDataSource {

  private final Context context;
  private final ConfigInfoResultUpdater configInfoResultUpdater;
  private final ConfigInfoResultReader configInfoResultReader;
  private final RealmDefaultInstance realmDefaultInstance;

  public TriggersConfigurationDBDataSourceImpl(Context context, ConfigInfoResultUpdater configInfoResultUpdater,
                                               ConfigInfoResultReader configInfoResultReader, RealmDefaultInstance realmDefaultInstance) {

    this.context = context;
    this.configInfoResultUpdater = configInfoResultUpdater;
    this.configInfoResultReader = configInfoResultReader;
    this.realmDefaultInstance = realmDefaultInstance;
  }

  public OrchextraUpdates saveConfigData(ConfigurationInfoResult configurationInfoResult) {

    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      realm.beginTransaction();
      OrchextraUpdates orchextraUpdates =
          configInfoResultUpdater.updateConfigInfoV2(realm, configurationInfoResult);
      return orchextraUpdates;
    } catch (Exception re) {
      re.printStackTrace();
      return null;
    } finally {
      realm.commitTransaction();
      if (realm != null) {
        realm.close();
      }
    }
  }

  public BusinessObject<ConfigurationInfoResult> obtainConfigData() {
    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      ConfigurationInfoResult configurationInfoResult = configInfoResultReader.readConfigInfo(realm);
      return new BusinessObject<>(configurationInfoResult, BusinessError.createOKInstance());
    } catch (NotFountRealmObjectException | RealmException re) {
      return new BusinessObject(null, BusinessError.createKoInstance(re.getMessage()));
    } finally {
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

  @Override public BusinessObject<List<OrchextraGeofence>> obtainGeofencesForRegister() {
    Realm realm = realmDefaultInstance.createRealmInstance(context);
    try {
      List<OrchextraGeofence> geofences = configInfoResultReader.getAllGeofences(realm);
      return new BusinessObject<>(geofences, BusinessError.createOKInstance());
    } catch (NotFountRealmObjectException | RealmException re) {
      return new BusinessObject(null, BusinessError.createKoInstance(re.getMessage()));
    } finally {
      if (realm != null) {
        realm.close();
      }
    }
  }

  @Override
  public BusinessObject<Object> removeLocalStorage() {
    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      configInfoResultUpdater.removeConfigInfo(realm);
      return new BusinessObject<>(null, BusinessError.createOKInstance());
    } catch (Exception re) {
      GGGLogImpl.log("No se ha eliminado correctamente la bas de datos: " + re.getMessage());
      return new BusinessObject(null, BusinessError.createKoInstance(re.getMessage()));
    } finally {
      if (realm != null) {
        realm.close();
      }
    }
  }

  @Override
  public BusinessObject<List<String>> retrieveCrmUserTags() {
    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      ConfigurationInfoResult configurationInfoResult = configInfoResultReader.readConfigInfo(realm);
      return new BusinessObject<>(configurationInfoResult.getCrmCustomFields().getTags(), BusinessError.createOKInstance());
    } catch (Exception re) {
      return new BusinessObject(null, BusinessError.createKoInstance(re.getMessage()));
    } finally {
      if (realm != null) {
        realm.close();
      }
    }
  }

  @Override
  public BusinessObject<List<String>> retrieveCrmDeviceTags() {
    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      ConfigurationInfoResult configurationInfoResult = configInfoResultReader.readConfigInfo(realm);
      return new BusinessObject<>(configurationInfoResult.getDeviceCustomFields().getTags(), BusinessError.createOKInstance());
    } catch (Exception re) {
      return new BusinessObject(null, BusinessError.createKoInstance(re.getMessage()));
    } finally {
      if (realm != null) {
        realm.close();
      }
    }
  }

  @Override
  public void saveCrmUserTags(List<String> userTagList) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      realm.beginTransaction();
      ConfigurationInfoResult configurationInfoResult = configInfoResultReader.readConfigInfo(realm);
      configurationInfoResult.getCrmCustomFields().setTags(userTagList);

      configInfoResultUpdater.updateConfigInfoV2(realm, configurationInfoResult);
    } catch (Exception re) {
      re.printStackTrace();
    } finally {
      if (realm != null) {
        realm.commitTransaction();
        realm.close();
      }
    }
  }

  @Override
  public void saveCrmDeviceUserTags(List<String> deviceTags) {
    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      realm.beginTransaction();
      ConfigurationInfoResult configurationInfoResult = configInfoResultReader.readConfigInfo(realm);
      configurationInfoResult.getDeviceCustomFields().setTags(deviceTags);

      configInfoResultUpdater.updateConfigInfoV2(realm, configurationInfoResult);
    } catch (Exception re) {
      re.printStackTrace();
    } finally {
      if (realm != null) {
        realm.commitTransaction();
        realm.close();
      }
    }
  }

  @Override public BusinessObject<OrchextraGeofence> obtainGeofenceById(String geofenceId) {

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
