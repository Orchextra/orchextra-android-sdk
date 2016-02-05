package com.gigigo.orchextra.initalization;

import com.gigigo.orchextra.initalization.features.Feature;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 2/2/16.
 */
public interface FeatureListener {
  void onFeatureStatusChanged(Feature feature);
}
