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

package com.gigigo.orchextra;

import java.util.Arrays;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

public class CrmUser {

    private final String crmId;
    private final GregorianCalendar birthdate;
    private final Gender gender;
    @Deprecated
    private List<String> keywords;
    @Deprecated
    private final List<CrmUserTag> tags;

    /**
     * Creates an orchextra user, this user will be useful for segmentation purposes and statistic
     * tracking in dashboard
     *
     * @param crmId     CrmUser ID, can be the user name of your app
     * @param birthdate user's birth date.
     * @param gender    user's male, using an enum
     * @param keywords  important words in order to segment specific actions
     */
    @Deprecated
    public CrmUser(String crmId, GregorianCalendar birthdate, Gender gender, List<String> keywords) {
        this(crmId, birthdate, gender);
        this.keywords = keywords;
    }

    /**
     * Creates an orchextra user, this user will be useful for segmentation purposes and statistic
     * tracking in dashboard
     *
     * @param crmId     CrmUser ID, can be the user name of your app
     * @param birthdate user's birth date.
     * @param gender    user's male, using an enum
     * @param tags      important Tags in order to segment specific actions
     */
    @Deprecated
    public CrmUser(String crmId, GregorianCalendar birthdate, Gender gender, CrmUserTag... tags) {
        this.crmId = crmId;
        this.birthdate = birthdate;
        this.gender = gender;
        this.tags = Arrays.asList(tags);
        this.keywords = Collections.EMPTY_LIST;
    }

    public String getCrmId() {
        return crmId;
    }

    public GregorianCalendar getBirthdate() {
        return birthdate;
    }

    public Gender getGender() {
        return gender;
    }

    @Deprecated
    public List<String> getKeywords() {
        return keywords;
    }

    @Deprecated
    public List<CrmUserTag> getTags() {
        return tags;
    }
    //todo REFACTOR this must be a GenderType and must to encapsulate el value of this field in server, ORCGenderMale-->"male"
    //OrcGenderConverter must be inside GenderType, i'm think
    public enum Gender {
        ORCGenderMale,
        ORCGenderFemale
    }
}
