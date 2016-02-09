package com.gigigo.orchextra.device.actions;

import com.gigigo.orchextra.device.notifications.dtos.AndroidBasicAction;
import com.gigigo.orchextra.device.notifications.dtos.mapper.AndroidBasicActionMapper;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 5/2/16.
 */
public class AndroidActionRecovery implements ActionRecovery {

  private final ActionDispatcher actionDispatcher;
  private final AndroidBasicActionMapper androidBasicActionMapper;

  public AndroidActionRecovery(ActionDispatcher actionDispatcher,
      AndroidBasicActionMapper androidBasicActionMapper) {
    this.actionDispatcher = actionDispatcher;
    this.androidBasicActionMapper = androidBasicActionMapper;
  }

  @Override public void recoverAction(AndroidBasicAction androidBasicAction) {
    BasicAction basicAction = androidBasicActionMapper.externalClassToModel(androidBasicAction);
    basicAction.performAction(actionDispatcher);
  }
}
