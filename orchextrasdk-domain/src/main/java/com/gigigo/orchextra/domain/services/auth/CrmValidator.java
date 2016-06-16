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

package com.gigigo.orchextra.domain.services.auth;

import com.gigigo.orchextra.domain.Validator;
import com.gigigo.orchextra.domain.abstractions.error.ErrorLogger;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;
import com.gigigo.orchextra.domain.model.entities.authentication.CrmTag;

import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrmValidator implements Validator<Crm> {

    private final ErrorLogger errorLogger;

    public CrmValidator(ErrorLogger errorLogger) {
        this.errorLogger = errorLogger;
    }

    @Override
    public void validate(Crm crm) {
        filterCrmKeywords(crm.getKeywords());
        filterCrmTags(crm.getTags());
    }

    @Deprecated
    private void filterCrmKeywords(List<String> keywords) {
        ListIterator<String> keyListIterator = keywords.listIterator();
        while (keyListIterator.hasNext()) {
            String keyword = keyListIterator.next();
            if (keywordIsNotValid(keyword)) {
                keyListIterator.remove();
                errorLogger.log("The provided keyword " + keyword + " is not valid");
            }
        }
    }

    @Deprecated
    private boolean keywordIsNotValid(String keyword) {
        String pattern = "^[a-zA-Z][a-zA-Z0-9_]*$";
        return !Pattern.matches(pattern, keyword);
    }

    @Deprecated
    private void filterCrmTags(List<CrmTag> tags) {
        ListIterator<CrmTag> tagListIterator = tags.listIterator();
        while (tagListIterator.hasNext()) {
            CrmTag tag = tagListIterator.next();
            if (tagIsNotValid(tag)) {
                tagListIterator.remove();
                errorLogger.log("The provided tag " + tag.toString() + " is not valid");
            }
        }
    }

    private boolean tagIsNotValid(CrmTag tag) {
        boolean bReturn = false;
        try {
            String prefixPattern = "(::|/|^_(?!(s$|b$))|^[^_].{0}$)";
            String namePattern = "(::|/|^_|^.{0,1}$)";

            Pattern ptPrefix = Pattern.compile(prefixPattern);
            Pattern ptName = Pattern.compile(namePattern);
            Matcher matcherPrefix = ptPrefix.matcher(tag.getPrefix());
            Matcher matcherName = ptName.matcher(tag.getName());

            if (matcherPrefix != null && matcherName != null) {
                bReturn = matcherPrefix.find() || matcherName.find();
            }

        } catch (Exception tr) {
            errorLogger.log("The provided tag " + tag.toString() + " cause error " + tr.getMessage());
        } finally {
            return bReturn;
        }

    }
}
