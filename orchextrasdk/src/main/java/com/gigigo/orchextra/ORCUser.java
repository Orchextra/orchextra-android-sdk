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
