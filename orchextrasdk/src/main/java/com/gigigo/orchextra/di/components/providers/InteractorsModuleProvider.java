package com.gigigo.orchextra.di.components.providers;

import com.gigigo.orchextra.domain.interactors.actions.GetActionInteractor;
import com.gigigo.orchextra.domain.interactors.authentication.AuthenticationInteractor;
import com.gigigo.orchextra.domain.interactors.config.SendConfigInteractor;
import com.gigigo.orchextra.domain.interactors.geofences.RetrieveGeofenceTriggerInteractor;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
public interface InteractorsModuleProvider {
  AuthenticationInteractor provideauthenticationInteractor();
  SendConfigInteractor provideSendConfigInteractor();
  GetActionInteractor provideGetActionInteractor();
  RetrieveGeofenceTriggerInteractor provideRetrieveGeofenceDistanceInteractor();
}
