package com.gigigo.orchextra.android;

import com.gigigo.orchextra.android.entities.AndroidBasicAction;
import com.gigigo.orchextra.android.mapper.AndroidBasicActionMapper;
import com.gigigo.orchextra.domain.entities.actions.strategy.BasicAction;
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
    BasicAction basicAction = androidBasicActionMapper.androidToModel(androidBasicAction);
    basicAction.performAction(actionDispatcher);
  }
}
