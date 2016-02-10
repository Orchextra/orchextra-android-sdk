package com.gigigo.orchextra.di.modules.control;

import com.gigigo.orchextra.control.controllers.config.ConfigObservable;
import com.gigigo.orchextra.control.controllers.proximity.ProximityItemController;
import com.gigigo.orchextra.di.modules.domain.DomainModuleProvider;
import com.gigigo.orchextra.di.qualifiers.BackThread;
import me.panavtec.threaddecoratedview.views.ThreadSpec;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/2/16.
 */
public interface ControlModuleProvider extends DomainModuleProvider{
  ConfigObservable provideConfigObservable();
  ProximityItemController provideProximityItemController();
  @BackThread ThreadSpec provideThreadSpec();
}
