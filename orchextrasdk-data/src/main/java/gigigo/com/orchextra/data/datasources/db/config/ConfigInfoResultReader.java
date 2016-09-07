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

import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.orchextra.dataprovision.config.model.strategy.ConfigurationInfoResult;
import com.gigigo.orchextra.domain.abstractions.device.OrchextraLogger;
import com.gigigo.orchextra.domain.model.entities.VuforiaCredentials;
import com.gigigo.orchextra.domain.model.entities.geofences.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.entities.tags.AvailableCustomField;
import com.gigigo.orchextra.domain.model.entities.tags.CrmCustomFields;
import com.gigigo.orchextra.domain.model.entities.tags.CustomField;
import com.gigigo.orchextra.domain.model.entities.tags.DeviceCustomFields;

import java.util.ArrayList;
import java.util.List;

import gigigo.com.orchextra.data.datasources.db.NotFountRealmObjectException;
import gigigo.com.orchextra.data.datasources.db.model.AvailableCustomFieldRealm;
import gigigo.com.orchextra.data.datasources.db.model.ConfigInfoResultRealm;
import gigigo.com.orchextra.data.datasources.db.model.CrmBusinessUnitRealm;
import gigigo.com.orchextra.data.datasources.db.model.CrmCustomFieldRealm;
import gigigo.com.orchextra.data.datasources.db.model.CrmTagRealm;
import gigigo.com.orchextra.data.datasources.db.model.DeviceBusinessUnitRealm;
import gigigo.com.orchextra.data.datasources.db.model.DeviceTagRealm;
import gigigo.com.orchextra.data.datasources.db.model.GeofenceRealm;
import gigigo.com.orchextra.data.datasources.db.model.RegionRealm;
import gigigo.com.orchextra.data.datasources.db.model.VuforiaCredentialsRealm;
import io.realm.Realm;
import io.realm.RealmResults;


public class ConfigInfoResultReader {

    private final ExternalClassToModelMapper<RegionRealm, OrchextraRegion> regionRealmMapper;
    private final ExternalClassToModelMapper<GeofenceRealm, OrchextraGeofence> geofencesRealmMapper;
    private final ExternalClassToModelMapper<VuforiaCredentialsRealm, VuforiaCredentials> vuforiaRealmMapper;

    private final OrchextraLogger orchextraLogger;
    private final ExternalClassToModelMapper<AvailableCustomFieldRealm, AvailableCustomField> availableCustomFieldMapper;

    @Deprecated
    public ConfigInfoResultReader(
            ExternalClassToModelMapper<RegionRealm, OrchextraRegion> regionRealmMapper,
            ExternalClassToModelMapper<GeofenceRealm, OrchextraGeofence> geofencesRealmMapper,
            ExternalClassToModelMapper<VuforiaCredentialsRealm, VuforiaCredentials> vuforiaRealmMapper,
            ExternalClassToModelMapper<AvailableCustomFieldRealm, AvailableCustomField> availableCustomFieldMapper,
            OrchextraLogger orchextraLogger) {

        this.regionRealmMapper = regionRealmMapper;
        this.geofencesRealmMapper = geofencesRealmMapper;
        this.vuforiaRealmMapper = vuforiaRealmMapper;
        this.availableCustomFieldMapper = availableCustomFieldMapper;

        this.orchextraLogger = orchextraLogger;
    }

    public ConfigurationInfoResult readConfigInfo(Realm realm) {

        ConfigInfoResultRealm config = readConfigObject(realm);

        VuforiaCredentials vuforiaCredentials = vuforiaRealmMapper.externalClassToModel(readVuforiaObject(realm));

        List<OrchextraGeofence> geofences = geofencesToModel(readGeofenceObjects(realm));
        List<OrchextraRegion> regions = regionsToModel(readRegionsObjects(realm));

        List<AvailableCustomField> availableCustomFieldList = availableCustomFieldToModel(readAvailableCustomFieldObjects(realm));

        CrmCustomFields crmCustomFields = crmCustomFieldsToModel(realm);

        DeviceCustomFields deviceCustomFields = deviceCustomFieldsToModel(realm);

        ConfigurationInfoResult configurationInfoResult =
                new ConfigurationInfoResult.Builder(config.getRequestWaitTime(), geofences, regions,
                        vuforiaCredentials, availableCustomFieldList, crmCustomFields, deviceCustomFields).build();

        orchextraLogger.log("Retrieved configurationInfoResult with properties"

                + " VuforiaCredentials :"
                + vuforiaCredentials.toString()
                + " Geofences :"
                + geofences.size()
                + " Regions :"
                + regions.size()
                + " Request Time :"
                + config.getRequestWaitTime());

        return configurationInfoResult;
    }

    private List<OrchextraRegion> regionsToModel(List<RegionRealm> regionRealms) {
        List<OrchextraRegion> regions = new ArrayList<>();
        for (RegionRealm regionRealm : regionRealms) {
            regions.add(regionRealmMapper.externalClassToModel(regionRealm));
        }
        return regions;
    }

    private List<OrchextraGeofence> geofencesToModel(List<GeofenceRealm> geofencesRealm) {
        List<OrchextraGeofence> geofences = new ArrayList<>();
        for (GeofenceRealm geofenceRealm : geofencesRealm) {
            geofences.add(geofencesRealmMapper.externalClassToModel(geofenceRealm));
        }
        return geofences;
    }

    private List<AvailableCustomField> availableCustomFieldToModel(List<AvailableCustomFieldRealm> availableCustomFieldRealmList) {
        List<AvailableCustomField> availableCustomFieldList = new ArrayList<>();
        for (AvailableCustomFieldRealm availableCustomFieldRealm : availableCustomFieldRealmList) {
            availableCustomFieldList.add(availableCustomFieldMapper.externalClassToModel(availableCustomFieldRealm));
        }
        return availableCustomFieldList;
    }

    private CrmCustomFields crmCustomFieldsToModel(Realm realm) {
        CrmCustomFields crmCustomFields = new CrmCustomFields();

        List<CrmTagRealm> crmTagRealmList = realm.where(CrmTagRealm.class).findAll();
        List<String> crmTagList = new ArrayList<>();
        for (CrmTagRealm crmTagRealm : crmTagRealmList) {
            crmTagList.add(crmTagRealm.getTag());
        }
        crmCustomFields.setTags(crmTagList);

        List<CrmBusinessUnitRealm> crmBusinessUnitRealmList = realm.where(CrmBusinessUnitRealm.class).findAll();
        List<String> crmBusinessUnitList = new ArrayList<>();
        for (CrmBusinessUnitRealm crmBusinessUnitRealm : crmBusinessUnitRealmList) {
            crmBusinessUnitList.add(crmBusinessUnitRealm.getBusinessUnit());
        }
        crmCustomFields.setBusinessUnits(crmBusinessUnitList);

        List<CrmCustomFieldRealm> crmCustomFieldRealmList = realm.where(CrmCustomFieldRealm.class).findAll();
        List<CustomField> customFieldList = new ArrayList<>();
        for (CrmCustomFieldRealm crmCustomFieldRealm : crmCustomFieldRealmList) {
            CustomField customField = new CustomField();
            customField.setKey(crmCustomFieldRealm.getKey());
            customField.setValue(crmCustomFieldRealm.getValue());
            customFieldList.add(customField);
        }
        crmCustomFields.setCustomFieldList(customFieldList);

        return crmCustomFields;
    }

    private DeviceCustomFields deviceCustomFieldsToModel(Realm realm) {
        DeviceCustomFields deviceCustomFields = new DeviceCustomFields();

        List<DeviceTagRealm> deviceTagRealmList = realm.where(DeviceTagRealm.class).findAll();
        List<String> deviceTagList = new ArrayList<>();
        for (DeviceTagRealm deviceTagRealm : deviceTagRealmList) {
            deviceTagList.add(deviceTagRealm.getTag());
        }
        deviceCustomFields.setTags(deviceTagList);

        List<DeviceBusinessUnitRealm> deviceBusinessUnitRealmList = realm.where(DeviceBusinessUnitRealm.class).findAll();
        List<String> deviceBusinessUnitList = new ArrayList<>();
        for (DeviceBusinessUnitRealm deviceBusinessUnitRealm : deviceBusinessUnitRealmList) {
            deviceBusinessUnitList.add(deviceBusinessUnitRealm.getBusinessUnit());
        }
        deviceCustomFields.setBusinessUnits(deviceBusinessUnitList);

        return deviceCustomFields;
    }

    private ConfigInfoResultRealm readConfigObject(Realm realm) {
        ConfigInfoResultRealm configInfo = realm.where(ConfigInfoResultRealm.class).findFirst();
        if (configInfo == null) {
            configInfo = new ConfigInfoResultRealm();
        }
        return configInfo;
    }

    private VuforiaCredentialsRealm readVuforiaObject(Realm realm) {
        return realm.where(VuforiaCredentialsRealm.class).findFirst();
    }

    private List<GeofenceRealm> readGeofenceObjects(Realm realm) {
        return realm.where(GeofenceRealm.class).findAll();
    }

    private List<RegionRealm> readRegionsObjects(Realm realm) {
        return realm.where(RegionRealm.class).findAll();
    }

    private List<AvailableCustomFieldRealm> readAvailableCustomFieldObjects(Realm realm) {
        return realm.where(AvailableCustomFieldRealm.class).findAll();
    }

    public OrchextraGeofence getGeofenceById(Realm realm, String geofenceId) {

        RealmResults<GeofenceRealm> geofenceRealm =
                realm.where(GeofenceRealm.class).equalTo("code", geofenceId).findAll();

        if (geofenceRealm.size() == 0) {
            orchextraLogger.log("Not found geofence with id: " + geofenceId);
            throw new NotFountRealmObjectException();
        } else {
            orchextraLogger.log("Found geofence with id: " + geofenceId);
            return geofencesRealmMapper.externalClassToModel(geofenceRealm.first());
        }
    }

    public List<OrchextraRegion> getAllRegions(Realm realm) {
        RealmResults<RegionRealm> regionRealms = realm.where(RegionRealm.class).findAll();
        List<OrchextraRegion> regions = new ArrayList<>();

        for (RegionRealm regionRealm : regionRealms) {
            regions.add(regionRealmMapper.externalClassToModel(regionRealm));
        }

        if (regions.size() > 0) {
            orchextraLogger.log("Retrieved " + regions.size() + " beacon regions");
        } else {
            orchextraLogger.log("Not Retrieved any region");
        }

        return regions;
    }



    public List<OrchextraGeofence> getAllGeofences(Realm realm) {
        RealmResults<GeofenceRealm> geofenceRealms = realm.where(GeofenceRealm.class).findAll();
        List<OrchextraGeofence> geofences = new ArrayList<>();

        for (GeofenceRealm geofenceRealm : geofenceRealms) {
            geofences.add(geofencesRealmMapper.externalClassToModel(geofenceRealm));
        }

        if (geofences.size() > 0) {
            orchextraLogger.log("Retrieved " + geofences.size() + " Geofences from DB");
        } else {
            orchextraLogger.log("Not Retrieved any Geofence");
        }

        return geofences;
    }
}
