package com.gigigo.orchextra.control.controllers.proximity.beacons;

import com.gigigo.orchextra.domain.entities.OrchextraRegion;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 25/1/16.
 */
public interface RegionsProviderListener {
  void onRegionsReady(List<OrchextraRegion> regions);
}
