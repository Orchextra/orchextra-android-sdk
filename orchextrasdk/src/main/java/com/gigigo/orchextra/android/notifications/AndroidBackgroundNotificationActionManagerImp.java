package com.gigigo.orchextra.android.notifications;

import android.app.Activity;
import android.content.Intent;

import com.gigigo.orchextra.android.entities.AndroidBasicAction;
import com.gigigo.orchextra.android.mapper.AndroidBasicActionMapper;
import com.gigigo.orchextra.domain.entities.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;

public class AndroidBackgroundNotificationActionManagerImp implements AndroidBackgroundNotificationActionManager {

    private final AndroidBasicActionMapper androidBasicActionMapper;
    private final ActionDispatcher actionDispatcher;

    public AndroidBackgroundNotificationActionManagerImp(AndroidBasicActionMapper androidBasicActionMapper,
                                                         ActionDispatcher actionDispatcher) {
        this.androidBasicActionMapper = androidBasicActionMapper;
        this.actionDispatcher = actionDispatcher;
    }

    @Override
    public void manageBackgroundNotification(Activity activity) {
        Intent intent = activity.getIntent();
        if (intent != null) {
            AndroidBasicAction androidBasicAction = intent.getParcelableExtra(AndroidNotificationBuilder.EXTRA_NOTIFICATION_ACTION);
            if (androidBasicAction != null) {
                BasicAction basicAction = androidBasicActionMapper.controlToModel(androidBasicAction);
                basicAction.performAction(actionDispatcher);
            }
        }
    }
}
