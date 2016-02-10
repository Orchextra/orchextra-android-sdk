package com.gigigo.orchextra.domain.abstractions.beacons;

import com.gigigo.orchextra.domain.model.entities.proximity.OrchextraRegion;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 25/1/16.
 */
public interface RegionsProviderListener {
  void onRegionsReady(List<OrchextraRegion> regions);
}
