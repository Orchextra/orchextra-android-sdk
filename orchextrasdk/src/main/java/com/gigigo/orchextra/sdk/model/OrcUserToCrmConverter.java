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

import com.gigigo.orchextra.ORCUser;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;

import java.util.ArrayList;
import java.util.List;

public class OrcUserToCrmConverter {

    private final OrcGenderConverter genderConverter;

    public OrcUserToCrmConverter(OrcGenderConverter genderConverter) {
        this.genderConverter = genderConverter;
    }

    public Crm convertOrcUserToCrm(ORCUser user) {
        Crm crm = new Crm();

        if (user != null) {
            crm.setCrmId(user.getCrmId());
            crm.setGender(genderConverter.convertGender(user.getGender()));

            if (user.getBirthdate() != null) {
                crm.setBirthDate(user.getBirthdate().getTime());
            }

            if (user.getTags() != null) {
                List<String> keywords = new ArrayList<>();
                for (String keyword : user.getTags()) {
                    if (!TextUtils.isEmpty(keyword)) {
                        keywords.add(keyword);
                    }
                }
                crm.setKeywords(keywords);
            }
        }
        return crm;
    }
}
