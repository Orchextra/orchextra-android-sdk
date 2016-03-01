package com.gigigo.orchextra.sdk.model;

import com.gigigo.orchextra.domain.model.GenderType;

public class OrcGenderConverter {

    public GenderType convertGender(ORCUser.Gender gender) {
        switch (gender) {
            case ORCGenderMale:
                return GenderType.MALE;
            case ORCGenderFemale:
                return GenderType.FEMALE;
            default:
                return GenderType.ND;
        }
    }
}
