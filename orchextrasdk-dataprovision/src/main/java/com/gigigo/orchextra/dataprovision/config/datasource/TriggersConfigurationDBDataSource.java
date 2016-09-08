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

package com.gigigo.orchextra.dataprovision.config.datasource;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.dataprovision.config.model.strategy.ConfigurationInfoResult;
import com.gigigo.orchextra.domain.model.entities.geofences.OrchextraGeofence;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraUpdates;
import com.gigigo.orchextra.domain.model.entities.tags.CustomField;

import java.util.List;


public interface TriggersConfigurationDBDataSource {
  OrchextraUpdates saveConfigData(ConfigurationInfoResult config);

  BusinessObject<ConfigurationInfoResult> obtainConfigData();

  BusinessObject<OrchextraGeofence> obtainGeofenceById(String uuid);

  BusinessObject<List<OrchextraRegion>> obtainRegionsForScan();

  BusinessObject<List<OrchextraGeofence>> obtainGeofencesForRegister();

  BusinessObject removeLocalStorage();

  BusinessObject<List<String>> retrieveCrmUserTags();

  BusinessObject<List<String>> retrieveCrmDeviceTags();

  void saveCrmUserTags(List<String> userTagList);

  void saveCrmDeviceUserTags(List<String> deviceTags);

  BusinessObject<List<String>> retrieveCrmDeviceBusinessUnits();

  void saveCrmDeviceBusinessUnits(List<String> deviceBusinessUnits);

  BusinessObject<List<String>> retrieveCrmUserBusinessUnits();

  void saveUserBusinessUnits(List<String> userBusinessUnits);

  BusinessObject<List<CustomField>> retrieveCrmUserCustomFields();

  void saveUserCustomFields(List<CustomField> customFieldList);
}
