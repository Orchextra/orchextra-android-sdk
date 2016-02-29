package com.gigigo.orchextra.device.actions;

import com.gigigo.orchextra.device.notifications.dtos.AndroidBasicAction;
import com.gigigo.orchextra.device.notifications.dtos.mapper.AndroidBasicActionMapper;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import me.panavtec.threaddecoratedview.views.ThreadSpec;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 5/2/16.
 */
public class AndroidActionRecovery implements ActionRecovery {

  private final ActionDispatcher actionDispatcher;
  private final AndroidBasicActionMapper androidBasicActionMapper;
  private final ThreadSpec mainThreadSpec;

  public AndroidActionRecovery(ActionDispatcher actionDispatcher,
      AndroidBasicActionMapper androidBasicActionMapper, ThreadSpec mainThreadSpec) {
    this.actionDispatcher = actionDispatcher;
    this.androidBasicActionMapper = androidBasicActionMapper;
    this.mainThreadSpec = mainThreadSpec;
  }

  @Override public void recoverAction(AndroidBasicAction androidBasicAction) {
    final BasicAction basicAction = androidBasicActionMapper.externalClassToModel(androidBasicAction);
    mainThreadSpec.execute(new Runnable() {
      @Override public void run() {
        basicAction.performAction(actionDispatcher);
      }
    });
  }
}
