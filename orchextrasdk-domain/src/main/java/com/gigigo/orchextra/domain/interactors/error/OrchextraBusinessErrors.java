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

package com.gigigo.orchextra.domain.interactors.error;

public enum OrchextraBusinessErrors {
  NO_AUTH_EXPIRED(401),
  NO_AUTH_CREDENTIALS(403),
  VALIDATION_ERROR(11200),
  INTERNAL_SERVER_ERROR(500),
  GENERIC_UNKNOWN_ERROR(-999);

  private final int codeError;

  OrchextraBusinessErrors(int codeError) {
    this.codeError = codeError;
  }

  public int getValue() {
    return codeError;
  }

  public static OrchextraBusinessErrors getEnumTypeFromInt(int errorCode) {
    for (OrchextraBusinessErrors error : OrchextraBusinessErrors.values()) {
      if (error.getValue() == errorCode) {
        return error;
      }
    }
    return GENERIC_UNKNOWN_ERROR;
  }
}
