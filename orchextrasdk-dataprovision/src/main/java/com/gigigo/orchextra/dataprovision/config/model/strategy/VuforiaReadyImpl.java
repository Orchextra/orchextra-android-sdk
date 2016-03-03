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

package com.gigigo.orchextra.dataprovision.config.model.strategy;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public class VuforiaReadyImpl implements VuforiaReady {

  private com.gigigo.orchextra.domain.model.entities.Vuforia vuforia;

  public VuforiaReadyImpl(com.gigigo.orchextra.domain.model.entities.Vuforia vuforia) {
    this.vuforia = vuforia;
  }

  @Override public com.gigigo.orchextra.domain.model.entities.Vuforia getVuforia() {
    return vuforia;
  }

  @Override public boolean isSupported() {
    return (vuforia == null) ? false : true;
  }
}
