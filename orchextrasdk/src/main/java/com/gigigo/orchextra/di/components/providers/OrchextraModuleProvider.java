package com.gigigo.orchextra.di.components.providers;

import com.gigigo.ggglib.ContextProvider;
import com.gigigo.orchextra.control.controllers.config.ConfigObservable;
import com.gigigo.orchextra.control.controllers.proximity.ProximityItemController;
import com.gigigo.orchextra.device.actions.ViewActionDispatcher;
import com.gigigo.orchextra.device.geolocation.geocoder.AndroidGeolocationManager;
import com.gigigo.orchextra.device.geolocation.geofencing.AndroidGeofenceManager;
import com.gigigo.orchextra.device.information.AndroidApp;
import com.gigigo.orchextra.device.information.AndroidDevice;
import com.gigigo.orchextra.di.qualifiers.BackThread;
import com.gigigo.orchextra.sdk.application.applifecycle.OrchextraActivityLifecycle;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppRunningMode;
import me.panavtec.threaddecoratedview.views.ThreadSpec;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
public interface OrchextraModuleProvider {
  AppRunningMode provideAppRunningModeType();
  ContextProvider provideContextProvider();
  ViewActionDispatcher provideViewActionDispatcher();
  OrchextraActivityLifecycle provideOrchextraActivityLifecycle();
  ConfigObservable provideConfigObservable();
  AndroidGeofenceManager provideAndroidGeofenceManager();
  ProximityItemController provideProximityItemController();
  AndroidApp provideAndroidApp();
  AndroidDevice provideAndroidDevice();
  AndroidGeolocationManager provideAndroidGeolocationManager();
  @BackThread ThreadSpec provideThreadSpec();
}
