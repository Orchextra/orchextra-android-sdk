package com.gigigo.orchextra.domain.abstractions.device;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 16/2/16.
 */
public interface GeolocationManager {
  void setRetrieveGeolocationListener(RetrieveGeolocationListener retrieveGeolocationListener);
  void retrieveGeolocation();
}
