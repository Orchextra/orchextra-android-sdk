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

package com.gigigo.orchextra.dataprovision.config.model.strategy;

import com.gigigo.orchextra.domain.model.entities.VuforiaCredentials;
import com.gigigo.orchextra.domain.model.entities.geofences.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.entities.tags.AvailableCustomField;
import com.gigigo.orchextra.domain.model.entities.tags.CrmCustomFields;
import com.gigigo.orchextra.domain.model.entities.tags.DeviceCustomFields;

import java.util.List;


public class ConfigurationInfoResult {

  private RegionListSupported regions;
  private GeofenceListSupported geofences;
  private VuforiaCredentialsSupported vuforia;
  private int requestWaitTime;
  private AvailableCustomFieldListSupportedImp availableCustomFieldList;
  private CrmCustomFieldsSupportedImp crmCustomFields;
  private DeviceCustomFieldsSupportedImp deviceCustomFields;

  public List<OrchextraGeofence> getGeofences() {
    return geofences.getGeofences();
  }

  public List<OrchextraRegion> getRegions() {
    return regions.getRegions();
  }

  public VuforiaCredentials getVuforia() {
    return vuforia.getVuforiaCredentials();
  }

  public boolean supportsVuforia() {
    return vuforia.isSupported();
  }

  public boolean supportsBeacons() {
    return regions.isSupported();
  }

  public boolean supportsGeofences() {
    return geofences.isSupported();
  }

  public int getRequestWaitTime() {
    return requestWaitTime;
  }

  public void setRequestWaitTime(int requestWaitTime) {
    this.requestWaitTime = requestWaitTime;
  }

  public void setRegions(RegionListSupported regions) {
    this.regions = regions;
  }

  public void setGeofences(GeofenceListSupported geofences) {
    this.geofences = geofences;
  }

  public void setVuforia(VuforiaCredentialsSupported vuforia) {
    this.vuforia = vuforia;
  }

  public void setAvailableCustomFieldList(AvailableCustomFieldListSupportedImp availableCustomFieldList) {
    this.availableCustomFieldList = availableCustomFieldList;
  }

  public void setCrmCustomFields(CrmCustomFieldsSupportedImp crmCustomFields) {
    this.crmCustomFields = crmCustomFields;
  }

  public void setDeviceCustomFields(DeviceCustomFieldsSupportedImp deviceCustomFields) {
    this.deviceCustomFields = deviceCustomFields;
  }

  public List<AvailableCustomField> getAvailableCustomFieldList() {
    return availableCustomFieldList.getAvailableCustomFieldList();
  }

  public boolean supportsAvailableCustomFieldList() {
    return availableCustomFieldList.isSupported();
  }

  public CrmCustomFields getCrmCustomFields() {
    return crmCustomFields.getCrmCustomFields();
  }

  public boolean supportsCrmCustomFields() {
    return crmCustomFields.isSupported();
  }

  public DeviceCustomFields getDeviceCustomFields() {
    return deviceCustomFields.getDeviceCustomFields();
  }

  public boolean supportsDeviceCustomFields() {
    return deviceCustomFields.isSupported();
  }

  public static class Builder {

    private final List<OrchextraGeofence> geoMarketing;
    private final List<OrchextraRegion> proximity;
    private final int requestWaitTime;
    private final VuforiaCredentials vuforiaCredentials;
    private final List<AvailableCustomField> availableCustomFieldTypeList;
    private final CrmCustomFields crmCustomFields;
    private final DeviceCustomFields deviceCustomFields;

    public Builder(int requestWaitTime,
                   List<OrchextraGeofence> geoMarketing,
                   List<OrchextraRegion> proximity,
                   VuforiaCredentials vuforiaCredentials,
                   List<AvailableCustomField> availableCustomFieldTypeList,
                   CrmCustomFields crmCustomFields,
                   DeviceCustomFields deviceCustomFields) {

      this.geoMarketing = geoMarketing;
      this.proximity = proximity;
      this.requestWaitTime = requestWaitTime;
      this.vuforiaCredentials = vuforiaCredentials;
      this.availableCustomFieldTypeList = availableCustomFieldTypeList;
      this.crmCustomFields = crmCustomFields;
      this.deviceCustomFields = deviceCustomFields;
    }

    public ConfigurationInfoResult build() {

      ConfigurationInfoResult configurationInfoResult = new ConfigurationInfoResult();

      configurationInfoResult.setRequestWaitTime(requestWaitTime);
      configurationInfoResult.setRegions(new RealRegionListSupportedImpl(proximity));
      configurationInfoResult.setGeofences(new RealGeofenceListSupportedImpl(geoMarketing));
      configurationInfoResult.setVuforia(new VuforiaCredentialsSupportedImpl(vuforiaCredentials));
      configurationInfoResult.setAvailableCustomFieldList(new AvailableCustomFieldListSupportedImp(availableCustomFieldTypeList));
      configurationInfoResult.setCrmCustomFields(new CrmCustomFieldsSupportedImp(crmCustomFields));
      configurationInfoResult.setDeviceCustomFields(new DeviceCustomFieldsSupportedImp(deviceCustomFields));

      return configurationInfoResult;
    }
  }
}
