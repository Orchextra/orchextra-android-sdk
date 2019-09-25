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

package com.gigigo.orchextrasdk.demo.ui.triggerlog.filter.adapter;

import androidx.annotation.NonNull;
import com.gigigo.orchextra.core.domain.entities.TriggerType;

public class TriggerFilter {

  @NonNull private boolean active;
  @NonNull private final TriggerType type;

  public TriggerFilter(@NonNull TriggerType type) {
    this.active = false;
    this.type = type;
  }

  @NonNull public boolean isActive() {
    return active;
  }

  public void setActive(@NonNull boolean active) {
    this.active = active;
  }

  @NonNull public TriggerType getType() {
    return type;
  }
}
