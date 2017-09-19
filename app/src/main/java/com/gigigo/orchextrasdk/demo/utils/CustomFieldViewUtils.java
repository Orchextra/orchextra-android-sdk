/*
 * Created by Orchextra
 *
 * Copyright (C) 2017 Gigigo Mobile Services SL
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

package com.gigigo.orchextrasdk.demo.utils;

import android.support.annotation.Nullable;
import com.gigigo.orchextrasdk.demo.utils.widget.CustomFieldView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomFieldViewUtils {

  private CustomFieldViewUtils() {
  }

  @Nullable public static Map<String, String> getCustomFieldsFromViewList(
      @Nullable List<CustomFieldView> customFieldViews) {

    if (customFieldViews == null || customFieldViews.isEmpty()) {
      return null;
    }

    Map<String, String> customFields = new HashMap<>();

    for (CustomFieldView customFieldView : customFieldViews) {
      if (!customFieldView.getValue().isEmpty()) {
        customFields.put(customFieldView.getCustomField().getKey(), customFieldView.getValue());
      }
    }

    if (customFields.isEmpty()) {
      return null;
    }

    return customFields;
  }
}
