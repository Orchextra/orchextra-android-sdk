package com.gigigo.orchextra.dataprovision.actions.datasource;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.entities.Action;
import com.gigigo.orchextra.domain.entities.ActionCriteria;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public interface ActionsDataSource {

  BusinessObject<Action> obtainAction(ActionCriteria actionCriteria);

}
