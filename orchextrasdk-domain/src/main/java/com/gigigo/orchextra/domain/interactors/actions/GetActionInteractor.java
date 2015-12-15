package com.gigigo.orchextra.domain.interactors.actions;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.ActionsDataProvider;
import com.gigigo.orchextra.domain.entities.Action;
import com.gigigo.orchextra.domain.entities.ActionCriteria;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
//TODO generic type can change
public class GetActionInteractor implements Interactor<InteractorResponse<Action>> {

  private final ActionsDataProvider actionsDataProvider;
  private ActionCriteria actionCriteria;

  public GetActionInteractor(ActionsDataProvider actionsDataProvider) {
    this.actionsDataProvider = actionsDataProvider;
  }

  public void addCriteriaIntoActionCriteria(String criteria) {
    //TODO add criteria;
  }

  public void setActionCriteria(ActionCriteria actionCriteria) {
    this.actionCriteria = actionCriteria;
  }

  @Override public InteractorResponse<Action> call() throws Exception {
    BusinessObject<Action> bo = actionsDataProvider.obtainAction(actionCriteria);
    //TODO error management
    return new InteractorResponse<>(bo.getData());
  }
}
