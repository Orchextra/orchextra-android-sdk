package com.gigigo.orchextra.domain.abstractions.device;

import com.gigigo.orchextra.domain.model.vo.GeoLocation;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 16/2/16.
 */
public interface RetrieveGeolocationListener {
  void retrieveGeolocation(GeoLocation geoLocation);
}
