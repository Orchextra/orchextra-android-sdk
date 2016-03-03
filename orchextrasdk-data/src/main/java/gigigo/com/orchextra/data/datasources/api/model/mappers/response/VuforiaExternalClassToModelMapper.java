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

package gigigo.com.orchextra.data.datasources.api.model.mappers.response;

import com.gigigo.ggglib.mappers.ExternalClassToModelMapper;
import com.gigigo.orchextra.domain.model.entities.Vuforia;
import gigigo.com.orchextra.data.datasources.api.model.responses.ApiVuforia;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public class VuforiaExternalClassToModelMapper
    implements ExternalClassToModelMapper<ApiVuforia, Vuforia> {

  @Override public Vuforia externalClassToModel(ApiVuforia apiVuforia) {
    Vuforia vuforia = new Vuforia();

    vuforia.setClientAccessKey(apiVuforia.getClientAccessKey());
    vuforia.setClientSecretKey(apiVuforia.getClientSecretKey());
    vuforia.setLicenseKey(apiVuforia.getLicenseKey());
    vuforia.setServerAccessKey(apiVuforia.getServerAccessKey());
    vuforia.setServerSecretKey(apiVuforia.getServerSecretKey());

    return vuforia;
  }
}
