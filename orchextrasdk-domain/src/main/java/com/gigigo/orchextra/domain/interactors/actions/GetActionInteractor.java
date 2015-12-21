package com.gigigo.orchextra.domain.interactors.actions;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.ActionsDataProvider;
import com.gigigo.orchextra.domain.entities.triggers.Trigger;
import com.gigigo.orchextra.domain.entities.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
//TODO generic type can change
public class GetActionInteractor implements Interactor<InteractorResponse<BasicAction>> {

  private final ActionsDataProvider actionsDataProvider;
  private Trigger actionCriteria;

  public GetActionInteractor(ActionsDataProvider actionsDataProvider) {
    this.actionsDataProvider = actionsDataProvider;
  }

  public void addCriteriaIntoActionCriteria(String criteria) {
    //TODO add criteria;
  }

  public void setActionCriteria(Trigger actionCriteria) {
    this.actionCriteria = actionCriteria;
  }

  @Override public InteractorResponse<BasicAction> call() throws Exception {
    BusinessObject<BasicAction> bo = actionsDataProvider.obtainAction(actionCriteria);
    //TODO error management
    return new InteractorResponse<>(bo.getData());
  }
}
