package com.gigigo.orchextra.di.components;

import com.gigigo.orchextra.domain.interactors.actions.GetActionInteractor;
import com.gigigo.orchextra.domain.interactors.authentication.AuthenticationInteractor;
import com.gigigo.orchextra.domain.interactors.config.SendConfigInteractor;
import com.gigigo.orchextra.domain.interactors.geofences.RetrieveGeofenceTriggerInteractor;
import com.gigigo.orchextra.domain.interactors.geofences.RetrieveGeofencesFromDatabaseInteractor;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 11/12/15.
 */
public interface InteractorsModuleProvider {
  AuthenticationInteractor provideauthenticationInteractor();
  SendConfigInteractor provideSendConfigInteractor();
  GetActionInteractor provideGetActionInteractor();
  RetrieveGeofencesFromDatabaseInteractor provideRetrieveGeofencesFromDatabaseInteractor();
  RetrieveGeofenceTriggerInteractor provideRetrieveGeofenceDistanceInteractor();
}
