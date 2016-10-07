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

package com.gigigo.orchextra.domain.model.actions;

import com.gigigo.orchextra.domain.model.StringValueEnum;

public enum ActionType implements StringValueEnum {
    BROWSER("browser"),
    WEBVIEW("webview"),
    CUSTOM_SCHEME("custom_scheme"),
    SCAN("scan"),
    VUFORIA_SCAN("scan_vuforia"),
    NOTIFICATION("notification"),
    NOTIFICATION_PUSH("notification_push"),
    NOT_DEFINED("do_nothing");

    private final String action;

    ActionType(final String text) {
        this.action = text;
    }

    @Override
    public String getStringValue() {
        return action;
    }

    public static ActionType getActionTypeValue(String type) {

        if (type == null) {
            return NOT_DEFINED;
        } else {
            if (type.equals(BROWSER.getStringValue())) {
                return BROWSER;
            } else if (type.equals(WEBVIEW.getStringValue())) {
                return WEBVIEW;
            } else if (type.equals(CUSTOM_SCHEME.getStringValue())) {
                return CUSTOM_SCHEME;
            } else if (type.equals(SCAN.getStringValue())) {
                return SCAN;
            } else if (type.equals(VUFORIA_SCAN.getStringValue())) {
                return VUFORIA_SCAN;
            } else if (type.equals(NOTIFICATION.getStringValue())) {
                return NOTIFICATION;
            } else if (type.equals(NOTIFICATION_PUSH.getStringValue())) {
                return NOTIFICATION_PUSH;
            } else {
                return NOT_DEFINED;
            }
        }
    }
}
