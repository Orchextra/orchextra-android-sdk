package com.gigigo.orchextra.domain.dataprovider;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.entities.Action;
import com.gigigo.orchextra.domain.entities.ActionCriteria;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public interface ActionsDataProvider {

  BusinessObject<Action> obtainAction(ActionCriteria actionCriteria);

}
