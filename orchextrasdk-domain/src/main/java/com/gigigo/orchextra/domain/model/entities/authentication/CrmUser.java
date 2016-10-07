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

package com.gigigo.orchextra.domain.model.entities.authentication;

import com.gigigo.orchextra.domain.model.GenderType;
import java.util.Date;


public class CrmUser {

    private String crmId;
    private GenderType gender;
    private Date birthDate;

    public CrmUser() {
    }

    public CrmUser(String crmId, GenderType gender, Date birthDate) {
        this.crmId = crmId;
        this.gender = gender;
        this.birthDate = birthDate;

    }

    public String getCrmId() {
        return crmId;
    }

    public void setCrmId(String crmId) {
        this.crmId = crmId;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isEquals(String crmId) {
        if (this.crmId == null) {
            return crmId == null;
        } else {
            return this.crmId.equals(crmId);
        }
    }

}
