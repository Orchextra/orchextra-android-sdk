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

import com.gigigo.gggjavalib.general.utils.DateUtils;
import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.ggglib.network.mappers.DateFormatConstants;
import com.gigigo.orchextra.domain.model.GenderType;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;
import gigigo.com.orchextra.data.datasources.db.model.CrmRealm;


public class CrmRealmMapper implements Mapper<Crm, CrmRealm> {

  private final KeyWordRealmMapper keyWordRealmMapper;

  public CrmRealmMapper(KeyWordRealmMapper keyWordRealmMapper) {
    this.keyWordRealmMapper = keyWordRealmMapper;
  }

  @Override public CrmRealm modelToExternalClass(Crm crm) {
    CrmRealm crmRealm = new CrmRealm();

    if (crm != null) {
      if (crm.getKeywords() != null) {
        crmRealm.setKeywords(keyWordRealmMapper.stringKeyWordsToRealmList(crm.getKeywords()));
      }

      if (crm.getBirthDate() != null) {
        crmRealm.setBirthDate(
            DateUtils.dateToStringWithFormat(crm.getBirthDate(), DateFormatConstants.DATE_FORMAT));
      }

      if (crm.getCrmId() != null) {
        crmRealm.setCrmId(crm.getCrmId());
      }

      if (crm.getGender() != null) {
        crmRealm.setGender(crm.getGender().getStringValue());
      }
    }

    return crmRealm;
  }

  @Override public Crm externalClassToModel(CrmRealm crmRealm) {
    Crm crm = new Crm();

    if (crmRealm != null) {

      if (!TextUtils.isEmpty(crmRealm.getCrmId())) {
        crm.setCrmId(crmRealm.getCrmId());
      }

      if (crmRealm.getKeywords() != null && crmRealm.getKeywords().size() > 0) {
        crm.setKeywords(keyWordRealmMapper.realmKeyWordsToStringList(crmRealm.getKeywords()));
      }

      if (!TextUtils.isEmpty(crmRealm.getGender())) {
        crm.setGender(GenderType.getTypeFromString(crmRealm.getGender()));
      }

      if (!TextUtils.isEmpty(crmRealm.getBirthDate())) {
        crm.setBirthDate(DateUtils.stringToDateWithFormat(crmRealm.getBirthDate(),
                DateFormatConstants.DATE_FORMAT));
      }
    }

    return crm;
  }
}
