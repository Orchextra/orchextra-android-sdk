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

package com.gigigo.orchextra.device.notifications.dtos.mapper;

import com.gigigo.ggglib.mappers.Mapper;
import com.gigigo.orchextra.device.notifications.dtos.AndroidBasicAction;
import com.gigigo.orchextra.domain.model.actions.ActionType;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;

public class AndroidBasicActionMapper implements Mapper<BasicAction, AndroidBasicAction> {

    private final AndroidNotificationMapper androidNotificationMapper;

    public AndroidBasicActionMapper(AndroidNotificationMapper androidNotificationMapper) {
        this.androidNotificationMapper = androidNotificationMapper;
    }

    @Override
    public AndroidBasicAction modelToExternalClass(BasicAction basicAction) {
        AndroidBasicAction androidBasicAction = new AndroidBasicAction();

        androidBasicAction.setId(basicAction.getId());
        androidBasicAction.setTrackId(basicAction.getTrackId());
        androidBasicAction.setAction(basicAction.getActionType().getStringValue());
        androidBasicAction.setUrl(basicAction.getUrl());

        androidBasicAction.setNotification(androidNotificationMapper.modelToExternalClass(
            basicAction.getNotificationBehavior()));

        return androidBasicAction;
    }

    @Override
    public BasicAction externalClassToModel(AndroidBasicAction androidBasicAction) {

        BasicAction.ActionBuilder actionBuilder = new BasicAction.ActionBuilder(
                androidBasicAction.getId(),
                androidBasicAction.getTrackId(),
                ActionType.getActionTypeValue(androidBasicAction.getAction()),
                androidBasicAction.getUrl(),
                androidNotificationMapper.externalClassToModel(androidBasicAction.getNotification()));

        return actionBuilder.build();
    }

}
