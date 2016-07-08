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

package gigigo.com.orchextra.data.datasources.db.model.mappers;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.entities.VuforiaCredentials;

import gigigo.com.orchextra.data.datasources.db.model.VuforiaCredentialsRealm;


public class VuforiaCredentialsRealmMapper implements Mapper<VuforiaCredentials, VuforiaCredentialsRealm> {

  @Override public VuforiaCredentialsRealm modelToExternalClass(VuforiaCredentials vuforiaCredentials) {
    VuforiaCredentialsRealm vuforiaCredentialsRealm = new VuforiaCredentialsRealm();

    if (vuforiaCredentials != null) {
      vuforiaCredentialsRealm.setClientAccessKey(vuforiaCredentials.getClientAccessKey());
      vuforiaCredentialsRealm.setClientSecretKey(vuforiaCredentials.getClientSecretKey());
      vuforiaCredentialsRealm.setLicenseKey(vuforiaCredentials.getLicenseKey());
    }

    return vuforiaCredentialsRealm;
  }

  @Override public VuforiaCredentials externalClassToModel(VuforiaCredentialsRealm vuforiaCredentialsRealm) {
    VuforiaCredentials vuforiaCredentials = new VuforiaCredentials();

    if (vuforiaCredentialsRealm != null) {
      vuforiaCredentials.setClientAccessKey(vuforiaCredentialsRealm.getClientAccessKey());
      vuforiaCredentials.setClientSecretKey(vuforiaCredentialsRealm.getClientSecretKey());
      vuforiaCredentials.setLicenseKey(vuforiaCredentialsRealm.getLicenseKey());
    }

    return vuforiaCredentials;
  }
}
