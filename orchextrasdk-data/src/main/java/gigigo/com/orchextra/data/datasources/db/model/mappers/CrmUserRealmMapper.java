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

import android.text.TextUtils;

import com.gigigo.gggjavalib.general.utils.DateFormatConstants;
import com.gigigo.gggjavalib.general.utils.DateUtils;
import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.domain.model.GenderType;
import com.gigigo.orchextra.domain.model.entities.authentication.CrmUser;
import gigigo.com.orchextra.data.datasources.db.model.CrmUserRealm;


public class CrmUserRealmMapper implements Mapper<CrmUser, CrmUserRealm> {
  @Deprecated
  private final KeyWordRealmMapper keyWordRealmMapper;

  public CrmUserRealmMapper(KeyWordRealmMapper keyWordRealmMapper) {
    this.keyWordRealmMapper = keyWordRealmMapper;
  }

  @Override public CrmUserRealm modelToExternalClass(CrmUser crmUser) {
    CrmUserRealm crmUserRealm = new CrmUserRealm();

    if (crmUser != null) {
      if (crmUser.getKeywords() != null) {
        crmUserRealm.setKeywords(keyWordRealmMapper.stringKeyWordsToRealmList(crmUser.getKeywords()));
      }

      if (crmUser.getBirthDate() != null) {
        crmUserRealm.setBirthDate(
            DateUtils.dateToStringWithFormat(crmUser.getBirthDate(), DateFormatConstants.DATE_FORMAT_TIME));
      }

      if (crmUser.getCrmId() != null) {
        crmUserRealm.setCrmId(crmUser.getCrmId());
      }

      if (crmUser.getGender() != null) {
        crmUserRealm.setGender(crmUser.getGender().getStringValue());
      }
    }

    return crmUserRealm;
  }

  @Override public CrmUser externalClassToModel(CrmUserRealm crmUserRealm) {
    CrmUser crmUser = new CrmUser();

    if (crmUserRealm != null) {

      if (!TextUtils.isEmpty(crmUserRealm.getCrmId())) {
        crmUser.setCrmId(crmUserRealm.getCrmId());
      }

      if (crmUserRealm.getKeywords() != null && crmUserRealm.getKeywords().size() > 0) {
        crmUser.setKeywords(keyWordRealmMapper.realmKeyWordsToStringList(crmUserRealm.getKeywords()));
      }

      if (!TextUtils.isEmpty(crmUserRealm.getGender())) {
        crmUser.setGender(GenderType.getTypeFromString(crmUserRealm.getGender()));
      }

      if (!TextUtils.isEmpty(crmUserRealm.getBirthDate())) {
        crmUser.setBirthDate(DateUtils.stringToDateWithFormat(crmUserRealm.getBirthDate(),
                DateFormatConstants.DATE_FORMAT_TIME));
      }
    }

    return crmUser;
  }
}
