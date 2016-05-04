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
package com.gigigo.orchextra.domain.abstractions.initialization;

import com.gigigo.orchextra.domain.model.StringValueEnum;

public enum StartStatusType implements StringValueEnum {
  UNKNOWN_START_STATUS("SDK is in a corrupted status"),
  SDK_WAS_ALREADY_STARTED_WITH_SAME_CREDENTIALS(
      "SDK was already started with the same credentials"),
  SDK_WAS_ALREADY_STARTED_WITH_DIFERENT_CREDENTIALS(
      "SDK already started with different credentials"),
  SDK_WAS_NOT_INITIALIZED("SDK was not initialized"),
  SDK_READY_FOR_START("SDK was not started, and now is ready for starting");

  private final String type;

  StartStatusType(final String type) {
    this.type = type;
  }

  public String getStringValue() {
    return type;
  }

}
