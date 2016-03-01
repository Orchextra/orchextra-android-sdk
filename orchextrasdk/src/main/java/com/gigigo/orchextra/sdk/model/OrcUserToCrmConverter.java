package com.gigigo.orchextra.sdk.model;

import android.text.TextUtils;

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
