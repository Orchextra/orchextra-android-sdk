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
import android.content.SharedPreferences;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.orchextra.dataprovision.config.datasource.TriggersConfigurationDBDataSource;
import com.gigigo.orchextra.dataprovision.config.model.strategy.ConfigurationInfoResult;
import com.gigigo.orchextra.domain.model.GenderType;
import com.gigigo.orchextra.domain.model.entities.authentication.ClientAuthData;
import com.gigigo.orchextra.domain.model.entities.authentication.CrmUser;
import com.gigigo.orchextra.domain.model.entities.authentication.SdkAuthData;
import com.gigigo.orchextra.domain.model.entities.credentials.ClientAuthCredentials;
import com.gigigo.orchextra.domain.model.entities.credentials.SdkAuthCredentials;
import com.gigigo.orchextra.domain.model.entities.geofences.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;
import com.gigigo.orchextra.domain.model.entities.tags.CustomField;

import java.util.ArrayList;
import java.util.List;

import gigigo.com.orchextra.data.datasources.db.NotFountRealmObjectException;
import gigigo.com.orchextra.data.datasources.db.RealmDefaultInstance;
import io.realm.Realm;
import io.realm.exceptions.RealmException;
//fixme refactor, avoid obtain/retrieve -->get remove/dlete save/store..

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


        //fixme REALM
        SharedPreferences prefs = this.context.getSharedPreferences("com.gigigo.orchextra", Context.MODE_PRIVATE);
        String strBIsConfigInitialized = "com.gigigo.orchextra:bIsConfigInitialized";
        boolean bIsSessionInitialized = prefs.getBoolean(strBIsConfigInitialized, false);
        if (!bIsSessionInitialized) {
            //fixme quizás mejor q dar de alta un registro con defaultvalues, sea mejor borrar lo q haia
     /* saveConfigData(new ConfigurationInfoResult());
      saveUserBusinessUnits(new ArrayList<String>());
      saveCrmDeviceBusinessUnits(new ArrayList<String>());
      saveCrmDeviceUserTags(new ArrayList<String>());
      saveCrmUserTags(new ArrayList<String>());
      saveUserCustomFields(new ArrayList<CustomField>());*/

            removeLocalStorage();

            prefs.edit().putBoolean(strBIsConfigInitialized, true);
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

    @Override
    public BusinessObject<List<OrchextraRegion>> obtainRegionsForScan() {
        Realm realm = realmDefaultInstance.createRealmInstance(context);
        List<OrchextraRegion> regions = configInfoResultReader.getAllRegions(realm);
        if (realm != null) {
            realm.close();
        }
        return new BusinessObject<>(regions, BusinessError.createOKInstance());
    }

    @Override
    public BusinessObject<List<OrchextraGeofence>> obtainGeofencesForRegister() {
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
            GGGLogImpl.log("No se ha eliminado correctamente la base de datos: " + re.getMessage());
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
    public BusinessObject<List<String>> retrieveCrmDeviceBusinessUnits() {
        Realm realm = realmDefaultInstance.createRealmInstance(context);

        try {
            ConfigurationInfoResult configurationInfoResult = configInfoResultReader.readConfigInfo(realm);
            return new BusinessObject<>(configurationInfoResult.getDeviceCustomFields().getBusinessUnits(), BusinessError.createOKInstance());
        } catch (Exception re) {
            return new BusinessObject(null, BusinessError.createKoInstance(re.getMessage()));
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    @Override
    public BusinessObject<List<CustomField>> retrieveCrmUserCustomFields() {
        Realm realm = realmDefaultInstance.createRealmInstance(context);

        try {
            ConfigurationInfoResult configurationInfoResult = configInfoResultReader.readConfigInfo(realm);
            return new BusinessObject<>(configurationInfoResult.getCrmCustomFields().getCustomFieldList(), BusinessError.createOKInstance());
        } catch (Exception re) {
            return new BusinessObject(null, BusinessError.createKoInstance(re.getMessage()));
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    @Override
    public BusinessObject<List<String>> retrieveCrmUserBusinessUnits() {
        Realm realm = realmDefaultInstance.createRealmInstance(context);

        try {
            ConfigurationInfoResult configurationInfoResult = configInfoResultReader.readConfigInfo(realm);
            return new BusinessObject<>(configurationInfoResult.getCrmCustomFields().getBusinessUnits(), BusinessError.createOKInstance());
        } catch (Exception re) {
            return new BusinessObject(null, BusinessError.createKoInstance(re.getMessage()));
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    @Override
    public BusinessObject<OrchextraGeofence> obtainGeofenceById(String geofenceId) {

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

    @Override
    public void saveUserBusinessUnits(List<String> userBusinessUnits) {
        Realm realm = realmDefaultInstance.createRealmInstance(context);

        try {
            realm.beginTransaction();
            ConfigurationInfoResult configurationInfoResult = configInfoResultReader.readConfigInfo(realm);
            configurationInfoResult.getCrmCustomFields().setBusinessUnits(userBusinessUnits);

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
    public void saveUserCustomFields(List<CustomField> customFieldList) {
        Realm realm = realmDefaultInstance.createRealmInstance(context);

        try {
            realm.beginTransaction();
            ConfigurationInfoResult configurationInfoResult = configInfoResultReader.readConfigInfo(realm);
            configurationInfoResult.getCrmCustomFields().setCustomFieldList(customFieldList);

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

    @Override
    public void saveCrmDeviceBusinessUnits(List<String> deviceBusinessUnits) {
        Realm realm = realmDefaultInstance.createRealmInstance(context);

        try {
            realm.beginTransaction();
            ConfigurationInfoResult configurationInfoResult = configInfoResultReader.readConfigInfo(realm);
            configurationInfoResult.getDeviceCustomFields().setBusinessUnits(deviceBusinessUnits);

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
}
