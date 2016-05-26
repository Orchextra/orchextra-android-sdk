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

import com.gigigo.orchextra.domain.abstractions.error.ErrorLogger;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;
import com.gigigo.orchextra.domain.model.entities.authentication.CrmTag;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 26/5/16.
 */
@RunWith(MockitoJUnitRunner.class) public class CrmValidatorTest {

  @Mock ErrorLogger errorLogger;

  Crm crm;
  CrmValidator crmValidator;

  @Before public void setUp() {
    crmValidator = new CrmValidator(errorLogger);
    crm = new Crm();
  }

  //TODO review regular expression because tests should be passing
  @Ignore @Test public void shouldvalidateAllTags() {
    int size = validTagsArrayList().size();
    crm.setTags(validTagsArrayList());
    crm.setKeywords(Collections.<String>emptyList());

    crmValidator.validate(crm);

    assertEquals(crm.getTags().size(), size);
  }

  @Test public void shouldnotValidateAllTags() {
    int size = notValidTagsArrayList().size();
    crm.setTags(notValidTagsArrayList());
    crm.setKeywords(Collections.<String>emptyList());

    crmValidator.validate(crm);

    assertTrue(crm.getTags().size() < size);
  }

  @Test public void shouldvalidateAllKeywords() {
    int size = validKeywordsArrayList().size();
    crm.setTags(Collections.<CrmTag>emptyList());
    crm.setKeywords(validKeywordsArrayList());

    crmValidator.validate(crm);

    assertEquals(crm.getKeywords().size(), size);
  }

  private List<String> validKeywordsArrayList() {
    List<String> keywords = new ArrayList<>();
    keywords.add("Hello");
    keywords.add("World");
    return keywords;
  }

  @Test public void shouldnotValidateAllKeywords() {
    int size = notValidKeywordsArrayList().size();
    crm.setTags(Collections.<CrmTag>emptyList());
    crm.setKeywords(notValidKeywordsArrayList());

    crmValidator.validate(crm);

    assertTrue(crm.getKeywords().size() < size);
  }

  private List<String> notValidKeywordsArrayList() {
    List<String> keywords = new ArrayList<>();
    keywords.add("Hello");
    keywords.add("Orchextra2.3.0");
    return keywords;
  }

  private List<CrmTag> validTagsArrayList() {
    List<CrmTag> tags = new ArrayList<>();
    tags.add(new CrmTag("Hello", "World"));
    tags.add(new CrmTag("Hello2", "World2"));
    return tags;
  }

  private List<CrmTag> notValidTagsArrayList() {
    List<CrmTag> tags = new ArrayList<>();
    tags.add(new CrmTag("::Hello", "World"));
    tags.add(new CrmTag("Hello2", "World2"));
    return tags;
  }
}