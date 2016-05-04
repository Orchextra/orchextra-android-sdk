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
import com.gigigo.orchextra.domain.model.entities.Vuforia;
import gigigo.com.orchextra.data.datasources.db.model.VuforiaRealm;


public class VuforiaRealmMapper implements Mapper<Vuforia, VuforiaRealm> {

  @Override public VuforiaRealm modelToExternalClass(Vuforia vuforia) {
    VuforiaRealm vuforiaRealm = new VuforiaRealm();

    if (vuforia != null) {
      vuforiaRealm.setClientAccessKey(vuforia.getClientAccessKey());
      vuforiaRealm.setClientSecretKey(vuforia.getClientSecretKey());
      vuforiaRealm.setLicenseKey(vuforia.getLicenseKey());
    }

    return vuforiaRealm;
  }

  @Override public Vuforia externalClassToModel(VuforiaRealm vuforiaRealm) {
    Vuforia vuforia = new Vuforia();

    if (vuforiaRealm != null) {
      vuforia.setClientAccessKey(vuforiaRealm.getClientAccessKey());
      vuforia.setClientSecretKey(vuforiaRealm.getClientSecretKey());
      vuforia.setLicenseKey(vuforiaRealm.getLicenseKey());
    }

    return vuforia;
  }
}
