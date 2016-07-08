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
package com.gigigo.orchextra.domain.abstractions.device;

import com.gigigo.orchextra.domain.model.StringValueEnum;

public enum OrchextraSDKLogLevel implements StringValueEnum {
    ALL(0),
    NETWORK(1),
    DEBUG(2),
    WARN(3),
    ERROR(4),
    NONE(5);

    private final int intValue;

    OrchextraSDKLogLevel(int intValue) {
        this.intValue = intValue;
    }

    public int intValue() {
        return intValue;
    }

    @Override
    public String getStringValue() {
        switch (intValue()) {
            case 0:
                return "ALL";
            case 1:
                return "NETWORK";
            case 2:
                return "DEBUG";
            case 3:
                return "WARN";
            case 4:
                return "ERROR";
            case 5:
            default:
                return "NONE";
        }

    }
}
