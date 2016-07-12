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

package com.gigigo.orchextra.sdk.model;

import android.text.TextUtils;

import com.gigigo.orchextra.CrmUser;
import com.gigigo.orchextra.CrmUserTag;

import com.gigigo.orchextra.domain.model.entities.authentication.CrmTag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrcUserToCrmConverter {

    private final OrcGenderConverter genderConverter;

    public OrcUserToCrmConverter(OrcGenderConverter genderConverter) {
        this.genderConverter = genderConverter;
    }

    public com.gigigo.orchextra.domain.model.entities.authentication.CrmUser convertOrcUserToCrm(CrmUser user) {
        com.gigigo.orchextra.domain.model.entities.authentication.CrmUser crmUser = new com.gigigo.orchextra.domain.model.entities.authentication.CrmUser();

        if (user != null) {
            crmUser.setCrmId(user.getCrmId());
            crmUser.setGender(genderConverter.convertGender(user.getGender()));

            if (user.getBirthdate() != null) {
                crmUser.setBirthDate(user.getBirthdate().getTime());
            }
            crmUser.setTags(obtainUserTags(user));
        }
        return crmUser;
    }

    @Deprecated
    private List<CrmTag> obtainUserTags(CrmUser user) {

        if (user.getTags() != null) {
            List<CrmTag> tags = new ArrayList<>();
            for (CrmUserTag orcTag : user.getTags()) {
                    tags.add(new CrmTag(orcTag.getPrefix(), orcTag.getValue()));
            }
            return tags;
        } else {
            return Collections.EMPTY_LIST;
        }

    }
}
