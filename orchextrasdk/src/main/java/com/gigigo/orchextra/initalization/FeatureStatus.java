package com.gigigo.orchextra.initalization;

import com.gigigo.orchextra.initalization.features.Feature;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 2/2/16.
 */
public interface FeatureStatus {

  List<Feature> getFeatures();
  boolean areAllFeaturesReady();
}
