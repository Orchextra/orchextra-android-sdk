package com.gigigo.orchextra.domain.model.actions.strategy;

import com.gigigo.gggjavalib.general.utils.Hashing;
import com.gigigo.orchextra.domain.model.actions.ScheduledAction;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/2/16.
 */
public class ScheduledActionImpl implements ScheduledAction {

  private final BasicAction basicAction;
  private final ScheduleFunctionality scheduleFunctionality;
  private String id = null;

  public ScheduledActionImpl(BasicAction basicAction) {
    this.scheduleFunctionality = basicAction.scheduleFunctionality;
    this.basicAction = basicAction;
  }

  @Override public boolean isCancelable() {
    return scheduleFunctionality.isCancelable();
  }

  @Override public long getScheduleTime() {
    return scheduleFunctionality.getScheduleTime();
  }

  @Override public String getId() {
    String id = basicAction.getId();

    //TODO delete when action getId be ready
    if (id != null && id != ""){
      return id;
    }else{
      return calculateFakeActionHash();
    }
  }

  private String calculateFakeActionHash() {

    try {
      return Hashing.generateMd5(
          basicAction.getActionType().getStringValue() +
              basicAction.getUrl() +
              basicAction.getEventCode() +
              ((basicAction.notifFunctionality.isSupported())?
                  (basicAction.getNotifFunctionality().getBody() +
                      basicAction.getNotifFunctionality().getTitle()) :
                  "" )
      );
    } catch (Exception e) {
      return basicAction.getEventCode();
    }

  }

  @Override public String getEventId(){
    return scheduleFunctionality.getSchedule().getEventId();
  }

  @Override public void setEventId(String id){
    scheduleFunctionality.getSchedule().setEventId(id);
  }

  @Override public BasicAction getBasicAction() {
    return basicAction;
  }
}
