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

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.entities.VuforiaCredentials;

import gigigo.com.orchextra.data.datasources.db.model.VuforiaCredentialsRealm;
import io.realm.Realm;
import io.realm.RealmResults;

public class ConfigVuforiaCredentialsUpdater {

  private final Mapper<VuforiaCredentials, VuforiaCredentialsRealm> vuforiaRealmMapper;

  public ConfigVuforiaCredentialsUpdater(Mapper<VuforiaCredentials, VuforiaCredentialsRealm> vuforiaRealmMapper) {
    this.vuforiaRealmMapper = vuforiaRealmMapper;
  }

  public VuforiaCredentials saveVuforia(Realm realm, VuforiaCredentials vuforiaCredentials) {
    boolean hasChangedVuforia = false;

    if (vuforiaCredentials != null) {
      hasChangedVuforia = checkIfChangedVuforia(realm, vuforiaCredentials);
    } else {
      realm.delete(VuforiaCredentialsRealm.class);
    }

    if (hasChangedVuforia) {
      return vuforiaCredentials;
    } else {
      return null;
    }
  }

  private boolean checkIfChangedVuforia(Realm realm, VuforiaCredentials vuforiaCredentials) {
    boolean hasChangedVuforia = false;

    VuforiaCredentialsRealm vuforiaCredentialsRealm = vuforiaRealmMapper.modelToExternalClass(vuforiaCredentials);

    RealmResults<VuforiaCredentialsRealm> savedVuforia = realm.where(VuforiaCredentialsRealm.class).findAll();
    if (savedVuforia.isEmpty()) {
      realm.copyToRealm(vuforiaCredentialsRealm);
      return true;
    }else if (savedVuforia.size() > 0){
      hasChangedVuforia = !checkVuforiaAreEquals(vuforiaCredentialsRealm, savedVuforia.first());
    }

    if (hasChangedVuforia) {
      realm.copyToRealmOrUpdate(vuforiaCredentialsRealm);
    }

    return hasChangedVuforia;
  }

  private boolean checkVuforiaAreEquals(VuforiaCredentialsRealm vuforiaCredentialsRealm, VuforiaCredentialsRealm oldVuforia) {
    if (oldVuforia == null) {
      return false;
    }

    String oldAccessKey = (oldVuforia.getClientAccessKey() != null)? oldVuforia.getClientAccessKey() : "";
    String oldSecretKey = (oldVuforia.getClientSecretKey() != null)? oldVuforia.getClientSecretKey() : "";
    String oldLicense = (oldVuforia.getLicenseKey() != null)? oldVuforia.getLicenseKey() : "";

    String accessKey = (vuforiaCredentialsRealm.getClientAccessKey() != null)? vuforiaCredentialsRealm.getClientAccessKey() : "";
    String secretKey = (vuforiaCredentialsRealm.getClientSecretKey() != null)? vuforiaCredentialsRealm.getClientSecretKey() : "";
    String license = (vuforiaCredentialsRealm.getLicenseKey() != null)? vuforiaCredentialsRealm.getLicenseKey() : "";

    return !(!oldAccessKey.equals(accessKey) || !oldSecretKey.equals(secretKey) || !oldLicense.equals(license));

  }

  public void removeVuforia(Realm realm) {
    if (realm != null) {
      realm.delete(VuforiaCredentialsRealm.class);
    }
  }
}
