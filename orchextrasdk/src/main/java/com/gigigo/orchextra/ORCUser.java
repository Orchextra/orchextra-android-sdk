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
import java.util.List;

public class ORCUser {

    private final String crmId;
    private final GregorianCalendar birthdate;
    private final Gender gender;
    private final List<String> tags;

    public ORCUser(String crmId, GregorianCalendar birthdate, Gender gender, List<String> tags) {
        this.crmId = crmId;
        this.birthdate = birthdate;
        this.gender = gender;
        this.tags = tags;
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

    public List<String> getTags() {
        return tags;
    }

    public enum Gender {
        ORCGenderMale,
        ORCGenderFemale
    }
}
