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

import com.gigigo.gggjavalib.general.utils.DateFormatConstants;
import com.gigigo.gggjavalib.general.utils.DateUtils;
import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.entities.authentication.SdkAuthData;
import gigigo.com.orchextra.data.datasources.db.model.SdkAuthRealm;


public class SdkAuthReamlMapper implements Mapper<SdkAuthData, SdkAuthRealm> {

  @Override public SdkAuthRealm modelToExternalClass(SdkAuthData sdkAuthData) {
    SdkAuthRealm sdkAuthRealm = new SdkAuthRealm();
    sdkAuthRealm.setExpiresAt(DateUtils.dateToStringWithFormat(sdkAuthData.getExpiresAt(),
        DateFormatConstants.DATE_FORMAT_TIME));
    sdkAuthRealm.setExpiresIn(sdkAuthData.getExpiresIn());
    sdkAuthRealm.setProjectId(sdkAuthData.getProjectId());
    sdkAuthRealm.setValue(sdkAuthData.getValue());
    return sdkAuthRealm;
  }

  @Override public SdkAuthData externalClassToModel(SdkAuthRealm sdkAuthRealm) {

    SdkAuthData sdkAuthdata = new SdkAuthData(sdkAuthRealm.getValue(), sdkAuthRealm.getExpiresIn(),
        DateUtils.stringToDateWithFormat(sdkAuthRealm.getExpiresAt(),
            DateFormatConstants.DATE_FORMAT_TIME), sdkAuthRealm.getProjectId());

    return sdkAuthdata;
  }
}
