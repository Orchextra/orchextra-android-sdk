package com.gigigo.orchextra.domain.abstractions.initialization.features;

import com.gigigo.orchextra.domain.initalization.features.Feature;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 2/2/16.
 */
public interface FeatureStatus {

  List<Feature> getFeatures();
  boolean areAllFeaturesReady();
}
