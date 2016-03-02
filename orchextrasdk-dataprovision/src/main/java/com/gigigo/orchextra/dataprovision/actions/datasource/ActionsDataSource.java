package com.gigigo.orchextra.dataprovision.actions.datasource;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.model.triggers.strategy.types.Trigger;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public interface ActionsDataSource {

  BusinessObject<BasicAction> obtainAction(Trigger actionCriteria);
}
