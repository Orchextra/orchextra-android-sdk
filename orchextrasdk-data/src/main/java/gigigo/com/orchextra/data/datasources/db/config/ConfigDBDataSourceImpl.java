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
import com.gigigo.orchextra.dataprovision.config.datasource.ConfigDBDataSource;
import com.gigigo.orchextra.dataprovision.config.model.strategy.ConfigInfoResult;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;
import com.gigigo.orchextra.domain.model.vo.Theme;

import java.util.List;

import gigigo.com.orchextra.data.datasources.db.NotFountRealmObjectException;
import gigigo.com.orchextra.data.datasources.db.RealmDefaultInstance;
import io.realm.Realm;
import io.realm.exceptions.RealmException;


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

  public OrchextraUpdates saveConfigData(ConfigInfoResult configInfoResult) {

    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      realm.beginTransaction();
      OrchextraUpdates orchextraUpdates =
          configInfoResultUpdater.updateConfigInfoV2(realm, configInfoResult);
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

  public BusinessObject<ConfigInfoResult> obtainConfigData() {
    Realm realm = realmDefaultInstance.createRealmInstance(context);

    try {
      ConfigInfoResult configInfoResult = configInfoResultReader.readConfigInfo(realm);
      return new BusinessObject<>(configInfoResult, BusinessError.createOKInstance());
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

  @Override
  public BusinessObject<Theme> obtainTheme() {
    Realm realm = realmDefaultInstance.createRealmInstance(context);
    try {
      Theme theme = configInfoResultReader.getTheme(realm);
      return new BusinessObject<>(theme, BusinessError.createOKInstance());
    } catch (NotFountRealmObjectException | RealmException re) {
      return new BusinessObject(null, BusinessError.createKoInstance(re.getMessage()));
    } finally {
      if (realm != null) {
        realm.close();
      }
    }
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
