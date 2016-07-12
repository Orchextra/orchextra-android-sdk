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
import com.gigigo.orchextra.domain.model.entities.authentication.CrmUser;
import com.gigigo.orchextra.domain.model.entities.authentication.CrmTag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
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
@RunWith(MockitoJUnitRunner.class) public class CrmUserUserFieldsValidatorTest {

  @Mock ErrorLogger errorLogger;

  CrmUser crmUser;
  CrmUserFieldsValidator crmUserFieldsValidator;

  @Before public void setUp() {
    crmUserFieldsValidator = new CrmUserFieldsValidator(errorLogger);
    crmUser = new CrmUser();
  }
  @Deprecated
  @Test public void shouldvalidateAllTags() {
    int size = validTagsArrayList().size();
    crmUser.setTags(validTagsArrayList());

    crmUserFieldsValidator.doValidate(crmUser);

    assertEquals(crmUser.getTags().size(), size);
  }
  @Deprecated
  @Test public void shouldnotValidateAllTags() {
    int size = notValidTagsArrayList().size();
    crmUser.setTags(notValidTagsArrayList());

    crmUserFieldsValidator.doValidate(crmUser);

    assertTrue(crmUser.getTags().size() < size);
  }
  @Deprecated
  @Test public void shoulValidateAllStrangeTags() {
    int size = allValidStrangeTagsArrayList().size();
    crmUser.setTags(allValidStrangeTagsArrayList());

    crmUserFieldsValidator.doValidate(crmUser);

    assertTrue(crmUser.getTags().size() == size);
  }
  @Deprecated
  @Test public void shouldnotValidateAnyTag() {
    crmUser.setTags(allNotValidTagsArrayList());

    crmUserFieldsValidator.doValidate(crmUser);

    assertTrue(crmUser.getTags().size() == 0);
  }



  @Deprecated
  private List<CrmTag> validTagsArrayList() {
    List<CrmTag> tags = new ArrayList<>();
    tags.add(new CrmTag("Hello", "World"));
    tags.add(new CrmTag("Hello2", "World2"));
    return tags;
  }
  @Deprecated
  private List<CrmTag> notValidTagsArrayList() {
    List<CrmTag> tags = new ArrayList<>();
    tags.add(new CrmTag("::Hello", "World"));
    tags.add(new CrmTag("Hello2", "World2"));
    return tags;
  }
  @Deprecated
  private List<CrmTag> allNotValidTagsArrayList() {
    List<CrmTag> tags = new ArrayList<>();

    tags.add(new CrmTag("::Hello", "World"));
    tags.add(new CrmTag("Hello2", "::World2"));
    tags.add(new CrmTag("Hello2", "W"));
    tags.add(new CrmTag("W", "Hello"));
    tags.add(new CrmTag("Hello/", "Hello"));
    tags.add(new CrmTag("Hello", "Hello/"));
    tags.add(new CrmTag("Hello", "_Hello"));
    tags.add(new CrmTag("_Hello", "Hello"));
    tags.add(new CrmTag("Hello", "_s"));
    tags.add(new CrmTag("Hello", "_b"));

    return tags;
  }
  @Deprecated
  private List<CrmTag> allValidStrangeTagsArrayList() {
    List<CrmTag> tags = new ArrayList<>();

    tags.add(new CrmTag("Hello.2", "Hello.1"));
    tags.add(new CrmTag("2Hello", "Hello"));
    tags.add(new CrmTag("Hello", "2Hello"));
    tags.add(new CrmTag("_s", "Hello"));
    tags.add(new CrmTag("_b", "Hello"));

    return tags;
  }


}