package com.gigigo.orchextra.android.mapper;

import com.gigigo.orchextra.android.entities.AndroidBasicAction;
import com.gigigo.orchextra.control.mapper.Mapper;
import com.gigigo.orchextra.domain.entities.actions.ActionType;
import com.gigigo.orchextra.domain.entities.actions.strategy.BasicAction;

public class AndroidBasicActionMapper implements Mapper<BasicAction, AndroidBasicAction> {

    private final AndroidNotificationMapper androidNotificationMapper;

    public AndroidBasicActionMapper(AndroidNotificationMapper androidNotificationMapper) {
        this.androidNotificationMapper = androidNotificationMapper;
    }

    @Override
    public AndroidBasicAction modelToControl(BasicAction basicAction) {
        AndroidBasicAction androidBasicAction = new AndroidBasicAction();

        androidBasicAction.setAction(basicAction.getActionType().getStringValue());
        androidBasicAction.setUrl(basicAction.getUrl());

        androidBasicAction.setNotification(androidNotificationMapper.modelToControl(basicAction.getNotifFunctionality()));

        return androidBasicAction;
    }

    @Override
    public BasicAction controlToModel(AndroidBasicAction androidBasicAction) {

        BasicAction.ActionBuilder actionBuilder = new BasicAction.ActionBuilder(
                ActionType.getActionTypeValue(androidBasicAction.getAction()),
                androidBasicAction.getUrl(),
                androidNotificationMapper.controlToModel(androidBasicAction.getNotification()));

        return actionBuilder.build();
    }
}
