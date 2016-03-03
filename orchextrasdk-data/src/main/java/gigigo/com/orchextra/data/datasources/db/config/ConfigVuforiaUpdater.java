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
import com.gigigo.orchextra.domain.model.entities.Vuforia;
import gigigo.com.orchextra.data.datasources.db.model.VuforiaRealm;
import io.realm.Realm;
import io.realm.RealmResults;

public class ConfigVuforiaUpdater {

  private final Mapper<Vuforia, VuforiaRealm> vuforiaRealmMapper;

  public ConfigVuforiaUpdater(Mapper<Vuforia, VuforiaRealm> vuforiaRealmMapper) {
    this.vuforiaRealmMapper = vuforiaRealmMapper;
  }

  public Vuforia saveVuforia(Realm realm, Vuforia vuforia) {
    boolean hasChangedVuforia = false;

    if (vuforia != null) {
      hasChangedVuforia = checkIfChangedVuforia(realm, vuforia);
    } else {
      realm.clear(VuforiaRealm.class);
    }

    if (hasChangedVuforia) {
      return vuforia;
    } else {
      return null;
    }
  }

  private boolean checkIfChangedVuforia(Realm realm, Vuforia vuforia) {
    boolean hasChangedVuforia = false;

    VuforiaRealm vuforiaRealm = vuforiaRealmMapper.modelToExternalClass(vuforia);

    RealmResults<VuforiaRealm> savedVuforia = realm.where(VuforiaRealm.class).findAll();
    if (savedVuforia.size() > 0) {
      hasChangedVuforia = !checkVuforiaAreEquals(vuforiaRealm, savedVuforia.first());
    }

    if (hasChangedVuforia) {
      realm.clear(VuforiaRealm.class);
      realm.copyToRealm(vuforiaRealm);
    }

    return hasChangedVuforia;
  }

  private boolean checkVuforiaAreEquals(VuforiaRealm vuforiaRealm, VuforiaRealm oldVuforia) {
    if (oldVuforia == null) {
      return false;
    }
    return vuforiaRealm.getClientAccessKey().equals(oldVuforia.getClientAccessKey()) &&
        vuforiaRealm.getClientSecretKey().equals(oldVuforia.getClientSecretKey()) &&
        vuforiaRealm.getLicenseKey().equals(oldVuforia.getLicenseKey()) &&
        vuforiaRealm.getServerAccessKey().equals(oldVuforia.getServerAccessKey()) &&
        vuforiaRealm.getServerSecretKey().equals(oldVuforia.getServerSecretKey());
  }
}
