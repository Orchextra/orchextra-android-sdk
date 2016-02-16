package com.gigigo.orchextra.domain.services.actions;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.ActionsDataProvider;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.interactors.error.ServiceErrorChecker;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.triggers.strategy.types.Trigger;
import com.gigigo.orchextra.domain.services.DomaninService;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
//TODO generic type can change
public class GetActionService implements DomaninService {

  private final ActionsDataProvider actionsDataProvider;
  private final ServiceErrorChecker serviceErrorChecker;

  public GetActionService(ActionsDataProvider actionsDataProvider, ServiceErrorChecker serviceErrorChecker) {

    this.actionsDataProvider = actionsDataProvider;
    this.serviceErrorChecker = serviceErrorChecker;
  }

  public InteractorResponse getActions(List<Trigger> triggers) {
    List<BasicAction> actions = new ArrayList<>();

    BusinessObject<BasicAction> bo = null;

    for (Trigger actionCriteria:triggers) {
      bo = actionsDataProvider.obtainAction(actionCriteria);

      if (bo.isSuccess()){
        actions.add(bo.getData());
      }else{
        break;
      }
    }

    if (bo != null && !bo.isSuccess()){
      boolean retry = manageError(bo.getBusinessError());
      if (retry) {
        return getActions(triggers);
      }
    }

    return new InteractorResponse<>(actions);
  }

  private boolean manageError(BusinessError businessError) {
    InteractorResponse response = serviceErrorChecker.checkErrors(businessError);
    return !response.hasError();
  }
}
