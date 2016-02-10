package com.gigigo.orchextra.domain.abstractions.initialization.features;

import com.gigigo.orchextra.domain.initalization.features.Feature;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 2/2/16.
 */
public interface FeatureListener {
  void onFeatureStatusChanged(Feature feature);
}
