package com.gigigo.orchextra.domain.initalization.features;

import com.gigigo.orchextra.domain.abstractions.initialization.*;
import com.gigigo.orchextra.domain.abstractions.initialization.features.FeatureListener;
import com.gigigo.orchextra.domain.abstractions.initialization.features.FeatureType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 2/2/16.
 */
public class FeatureList implements FeatureListener,
    com.gigigo.orchextra.domain.abstractions.initialization.features.FeatureStatus {

  private List<Feature> features = new ArrayList<>();
  private final OrchextraCompletionCallback orchextraCompletionCallback;

  public FeatureList(OrchextraCompletionCallback orchextraCompletionCallback) {
    this.orchextraCompletionCallback = orchextraCompletionCallback;
  }

  @Override public void onFeatureStatusChanged(Feature feature) {

    if (features.contains(feature)){
      int index = features.indexOf(feature);
      features.get(index).setStatus(feature.getStatus());
    }else{
      features.add(feature);
      checkAllFeaturesAdded();
    }

  }

  private void checkAllFeaturesAdded() {
    if (areAllFeaturesReady() && orchextraCompletionCallback != null){
      if (areSuccess()){
        orchextraCompletionCallback.onSuccess();
      }else{
        orchextraCompletionCallback.onError(formatErrorsAsString());
      }
    }
  }

  private String formatErrorsAsString() {
    return toString();
  }

  private boolean areSuccess() {
    for(Feature feature:features){
      if (!feature.isSuccess()){
        return false;
      }
    }
    return true;
  }

  @Override public List<Feature> getFeatures() {
    return features;
  }

  @Override public boolean areAllFeaturesReady() {
    return features.size() == FeatureType.values().length;
  }

  @Override public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Feature feature:features){
      sb.append(feature.toString() + "\n");
    }
    return sb.toString();
  }
}