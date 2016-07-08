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
import gigigo.com.orchextra.data.datasources.db.model.CrmRealm;


public class CrmRealmMapper implements Mapper<CrmUser, CrmRealm> {
  @Deprecated
  private final KeyWordRealmMapper keyWordRealmMapper;

  public CrmRealmMapper(KeyWordRealmMapper keyWordRealmMapper) {
    this.keyWordRealmMapper = keyWordRealmMapper;
  }

  @Override public CrmRealm modelToExternalClass(CrmUser crmUser) {
    CrmRealm crmRealm = new CrmRealm();

    if (crmUser != null) {
      if (crmUser.getKeywords() != null) {
        crmRealm.setKeywords(keyWordRealmMapper.stringKeyWordsToRealmList(crmUser.getKeywords()));
      }

      if (crmUser.getBirthDate() != null) {
        crmRealm.setBirthDate(
            DateUtils.dateToStringWithFormat(crmUser.getBirthDate(), DateFormatConstants.DATE_FORMAT_TIME));
      }

      if (crmUser.getCrmId() != null) {
        crmRealm.setCrmId(crmUser.getCrmId());
      }

      if (crmUser.getGender() != null) {
        crmRealm.setGender(crmUser.getGender().getStringValue());
      }
    }

    return crmRealm;
  }

  @Override public CrmUser externalClassToModel(CrmRealm crmRealm) {
    CrmUser crmUser = new CrmUser();

    if (crmRealm != null) {

      if (!TextUtils.isEmpty(crmRealm.getCrmId())) {
        crmUser.setCrmId(crmRealm.getCrmId());
      }

      if (crmRealm.getKeywords() != null && crmRealm.getKeywords().size() > 0) {
        crmUser.setKeywords(keyWordRealmMapper.realmKeyWordsToStringList(crmRealm.getKeywords()));
      }

      if (!TextUtils.isEmpty(crmRealm.getGender())) {
        crmUser.setGender(GenderType.getTypeFromString(crmRealm.getGender()));
      }

      if (!TextUtils.isEmpty(crmRealm.getBirthDate())) {
        crmUser.setBirthDate(DateUtils.stringToDateWithFormat(crmRealm.getBirthDate(),
                DateFormatConstants.DATE_FORMAT_TIME));
      }
    }

    return crmUser;
  }
}
