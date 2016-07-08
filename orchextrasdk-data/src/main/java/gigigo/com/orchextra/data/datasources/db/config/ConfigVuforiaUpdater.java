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

import gigigo.com.orchextra.data.datasources.db.model.VuforiaRealm;
import io.realm.Realm;
import io.realm.RealmResults;

public class ConfigVuforiaUpdater {

  private final Mapper<VuforiaCredentials, VuforiaRealm> vuforiaRealmMapper;

  public ConfigVuforiaUpdater(Mapper<VuforiaCredentials, VuforiaRealm> vuforiaRealmMapper) {
    this.vuforiaRealmMapper = vuforiaRealmMapper;
  }

  public VuforiaCredentials saveVuforia(Realm realm, VuforiaCredentials vuforiaCredentials) {
    boolean hasChangedVuforia = false;

    if (vuforiaCredentials != null) {
      hasChangedVuforia = checkIfChangedVuforia(realm, vuforiaCredentials);
    } else {
      realm.delete(VuforiaRealm.class);
    }

    if (hasChangedVuforia) {
      return vuforiaCredentials;
    } else {
      return null;
    }
  }

  private boolean checkIfChangedVuforia(Realm realm, VuforiaCredentials vuforiaCredentials) {
    boolean hasChangedVuforia = false;

    VuforiaRealm vuforiaRealm = vuforiaRealmMapper.modelToExternalClass(vuforiaCredentials);

    RealmResults<VuforiaRealm> savedVuforia = realm.where(VuforiaRealm.class).findAll();
    if (savedVuforia.isEmpty()) {
      realm.copyToRealm(vuforiaRealm);
      return true;
    }else if (savedVuforia.size() > 0){
      hasChangedVuforia = !checkVuforiaAreEquals(vuforiaRealm, savedVuforia.first());
    }

    if (hasChangedVuforia) {
      realm.copyToRealmOrUpdate(vuforiaRealm);
    }

    return hasChangedVuforia;
  }

  private boolean checkVuforiaAreEquals(VuforiaRealm vuforiaRealm, VuforiaRealm oldVuforia) {
    if (oldVuforia == null) {
      return false;
    }

    String oldAccessKey = (oldVuforia.getClientAccessKey() != null)? oldVuforia.getClientAccessKey() : "";
    String oldSecretKey = (oldVuforia.getClientSecretKey() != null)? oldVuforia.getClientSecretKey() : "";
    String oldLicense = (oldVuforia.getLicenseKey() != null)? oldVuforia.getLicenseKey() : "";

    String accessKey = (vuforiaRealm.getClientAccessKey() != null)? vuforiaRealm.getClientAccessKey() : "";
    String secretKey = (vuforiaRealm.getClientSecretKey() != null)? vuforiaRealm.getClientSecretKey() : "";
    String license = (vuforiaRealm.getLicenseKey() != null)? vuforiaRealm.getLicenseKey() : "";

    if (!oldAccessKey.equals(accessKey) || !oldSecretKey.equals(secretKey) || !oldLicense.equals(license)){
      return false;
    }

    return true;
  }

  public void removeVuforia(Realm realm) {
    if (realm != null) {
      realm.delete(VuforiaRealm.class);
    }
  }
}
