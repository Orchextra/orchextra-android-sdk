package com.gigigo.orchextra.android.mapper;

import com.gigigo.orchextra.android.entities.AndroidBasicAction;
import com.gigigo.orchextra.domain.entities.actions.ActionType;
import com.gigigo.orchextra.domain.entities.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.mappers.Mapper;

public class AndroidBasicActionMapper implements Mapper<BasicAction, AndroidBasicAction> {

    private final AndroidNotificationMapper androidNotificationMapper;

    public AndroidBasicActionMapper(AndroidNotificationMapper androidNotificationMapper) {
        this.androidNotificationMapper = androidNotificationMapper;
    }

    @Override
    public AndroidBasicAction modelToAndroid(BasicAction basicAction) {
        AndroidBasicAction androidBasicAction = new AndroidBasicAction();

        androidBasicAction.setAction(basicAction.getActionType().getStringValue());
        androidBasicAction.setUrl(basicAction.getUrl());

        androidBasicAction.setNotification(androidNotificationMapper.modelToAndroid(basicAction.getNotifFunctionality()));

        return androidBasicAction;
    }

    @Override
    public BasicAction androidToModel(AndroidBasicAction androidBasicAction) {

        BasicAction.ActionBuilder actionBuilder = new BasicAction.ActionBuilder(
                ActionType.getActionTypeValue(androidBasicAction.getAction()),
                androidBasicAction.getUrl(),
                androidNotificationMapper.androidToModel(androidBasicAction.getNotification()));

        return actionBuilder.build();
    }

}
