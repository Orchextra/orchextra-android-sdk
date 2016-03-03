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

import com.gigigo.gggjavalib.general.utils.DateUtils;
import com.gigigo.ggglib.mappers.ModelToExternalClassMapper;
import com.gigigo.ggglib.network.mappers.DateFormatConstants;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;
import gigigo.com.orchextra.data.datasources.api.model.requests.ApiCrm;


public class CrmModelToExternalClassMapper implements ModelToExternalClassMapper<Crm, ApiCrm> {

  @Override public ApiCrm modelToExternalClass(Crm crm) {

    ApiCrm apiCrm = new ApiCrm();

    apiCrm.setBirthDate(
        DateUtils.dateToStringWithFormat(crm.getBirthDate(), DateFormatConstants.DATE_FORMAT));
    apiCrm.setCrmId(crm.getCrmId());

    if (crm.getGender() != null) {
      apiCrm.setGender(crm.getGender().getStringValue());
    }

    apiCrm.setKeywords(crm.getKeywords());

    return apiCrm;
  }
}
