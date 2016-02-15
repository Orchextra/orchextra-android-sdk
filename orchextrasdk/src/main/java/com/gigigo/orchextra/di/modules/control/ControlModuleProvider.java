package com.gigigo.orchextra.di.modules.control;

import com.gigigo.orchextra.control.controllers.config.ConfigObservable;
import com.gigigo.orchextra.control.controllers.proximity.geofence.GeofenceController;
import com.gigigo.orchextra.di.modules.domain.DomainModuleProvider;
import com.gigigo.orchextra.di.qualifiers.BackThread;
import me.panavtec.threaddecoratedview.views.ThreadSpec;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 9/2/16.
 */
public interface ControlModuleProvider extends DomainModuleProvider{
  ConfigObservable provideConfigObservable();
  GeofenceController provideProximityItemController();
  @BackThread ThreadSpec provideThreadSpec();
}
