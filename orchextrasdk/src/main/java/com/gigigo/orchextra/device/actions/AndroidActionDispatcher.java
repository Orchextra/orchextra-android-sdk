package com.gigigo.orchextra.device.actions;

import com.gigigo.orchextra.device.notifications.dtos.AndroidBasicAction;
import com.gigigo.orchextra.device.notifications.dtos.mapper.AndroidBasicActionMapper;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 5/2/16.
 */
public class AndroidActionDispatcher implements ViewActionDispatcher {

  private final ActionDispatcher actionDispatcher;
  private final AndroidBasicActionMapper androidBasicActionMapper;

  public AndroidActionDispatcher(ActionDispatcher actionDispatcher,
      AndroidBasicActionMapper androidBasicActionMapper) {
    this.actionDispatcher = actionDispatcher;
    this.androidBasicActionMapper = androidBasicActionMapper;
  }

  @Override public void dispatchViewAction(AndroidBasicAction androidBasicAction) {
    BasicAction basicAction = androidBasicActionMapper.externalClassToModel(androidBasicAction);
    basicAction.performAction(actionDispatcher);
  }
}
