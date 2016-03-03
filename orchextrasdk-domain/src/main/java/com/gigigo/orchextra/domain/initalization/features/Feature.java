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

import com.gigigo.orchextra.domain.abstractions.initialization.features.FeatureType;
import com.gigigo.orchextra.domain.model.StringValueEnum;


public abstract class Feature {

  private FeatureType featureType;
  private StringValueEnum status;

  public Feature(
      com.gigigo.orchextra.domain.abstractions.initialization.features.FeatureType featureType,
      StringValueEnum status) {
    this.featureType = featureType;
    this.status = status;
  }

  public void setStatus(StringValueEnum status) {
    this.status = status;
  }

  public StringValueEnum getStatus() {
    return status;
  }

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Feature feature = (Feature) o;

    return featureType == feature.featureType;
  }

  @Override public int hashCode() {
    return featureType != null ? featureType.hashCode() : 0;
  }

  public abstract boolean isSuccess();

  @Override public String toString() {
    return "Feature --> "
        + featureType.getStringValue()
        + " :: " + status.getStringValue();
  }
}
