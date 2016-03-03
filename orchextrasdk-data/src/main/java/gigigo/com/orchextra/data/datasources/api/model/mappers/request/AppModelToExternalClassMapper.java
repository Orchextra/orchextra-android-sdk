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

package gigigo.com.orchextra.data.datasources.api.model.mappers.request;

import com.gigigo.ggglib.mappers.ModelToExternalClassMapper;
import com.gigigo.orchextra.domain.model.entities.App;
import gigigo.com.orchextra.data.datasources.api.model.requests.ApiApp;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public class AppModelToExternalClassMapper implements ModelToExternalClassMapper<App, ApiApp> {

  @Override public ApiApp modelToExternalClass(App app) {
    ApiApp apiApp = new ApiApp();

    apiApp.setAppVersion(app.getAppVersion());
    apiApp.setBuildVersion(app.getBuildVersion());
    apiApp.setBundleId(app.getBundleId());

    return apiApp;
  }
}
