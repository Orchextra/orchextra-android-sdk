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

        androidBasicAction.setAction(basicAction.getActionType().getStringValue());
        androidBasicAction.setUrl(basicAction.getUrl());

        androidBasicAction.setNotification(androidNotificationMapper.modelToExternalClass(
            basicAction.getNotifFunctionality()));

        return androidBasicAction;
    }

    @Override
    public BasicAction externalClassToModel(AndroidBasicAction androidBasicAction) {

        BasicAction.ActionBuilder actionBuilder = new BasicAction.ActionBuilder(
                ActionType.getActionTypeValue(androidBasicAction.getAction()),
                androidBasicAction.getUrl(),
                androidNotificationMapper.externalClassToModel(androidBasicAction.getNotification()));

        return actionBuilder.build();
    }

}
