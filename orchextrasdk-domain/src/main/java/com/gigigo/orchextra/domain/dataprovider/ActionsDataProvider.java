package com.gigigo.orchextra.domain.dataprovider;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.entities.triggers.Trigger;
import com.gigigo.orchextra.domain.entities.actions.strategy.BasicAction;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 15/12/15.
 */
public interface ActionsDataProvider {

  BusinessObject<BasicAction> obtainAction(Trigger actionCriteria);

}
