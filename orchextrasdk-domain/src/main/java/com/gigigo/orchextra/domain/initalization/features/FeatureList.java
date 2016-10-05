/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigigo.orchextra.domain.initalization.features;

import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraManagerCompletionCallback;
import com.gigigo.orchextra.domain.abstractions.initialization.features.FeatureListener;
import com.gigigo.orchextra.domain.abstractions.initialization.features.FeatureType;
import java.util.ArrayList;
import java.util.List;
//todo notcomplete
public class FeatureList implements FeatureListener,
    com.gigigo.orchextra.domain.abstractions.initialization.features.FeatureStatus {

  private List<Feature> features = new ArrayList<>();
  private final OrchextraManagerCompletionCallback orchextraCompletionCallback;

  public FeatureList(OrchextraManagerCompletionCallback orchextraCompletionCallback) {
    this.orchextraCompletionCallback = orchextraCompletionCallback;
  }

  @Override public void onFeatureStatusChanged(Feature feature) {

    if (features.contains(feature)) {
      int index = features.indexOf(feature);
      features.get(index).setStatus(feature.getStatus());
    } else {
      features.add(feature);
      checkAllFeaturesAdded();
    }
  }

  private void checkAllFeaturesAdded() {
    if (areAllFeaturesReady() && orchextraCompletionCallback != null) {
      if (areSuccess()) {
        orchextraCompletionCallback.onSuccess(); //THIS NEVER USED
      } else {
        orchextraCompletionCallback.onError(formatErrorsAsString());  //todo this line throw not correct error, when all features are added yet
      }
    }
  }

  private String formatErrorsAsString() {
    return toString();
  }

  private boolean areSuccess() {
    for (Feature feature : features) {
      if (!feature.isSuccess()) {
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
    for (Feature feature : features) {
      sb.append(feature.toString() + "\n");
    }
    return sb.toString();
  }
}