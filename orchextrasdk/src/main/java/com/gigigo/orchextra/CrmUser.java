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


import java.util.GregorianCalendar;


public class CrmUser {

    private final String crmId;
    private final GregorianCalendar birthdate;
    private final Gender gender;

    /**
     * Creates an orchextra user, this user will be useful for segmentation purposes and statistic
     * tracking in dashboard
     *
     * @param crmId     CrmUser ID, can be the user name of your app
     * @param birthdate user's birth date.
     * @param gender    user's male, using an enum
     */
    public CrmUser(String crmId, GregorianCalendar birthdate, Gender gender) {
        this.crmId = crmId;
        this.birthdate = birthdate;
        this.gender = gender;
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

    //todo REFACTOR this must be a GenderType and must to encapsulate el value of this field in server, GenderMale-->"male"
    //CrmUserGenderConverter must be inside GenderType, i'm think
    public enum Gender {
        GenderMale,
        GenderFemale,
        GenderND

    }
}
