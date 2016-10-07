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

package com.gigigo.orchextra.delegates;

import com.gigigo.orchextra.control.controllers.config.ConfigController;

public class ConfigDelegateImpl {

  private final ConfigController configController;

  public ConfigDelegateImpl(ConfigController configController) {
    this.configController = configController;
  }

  public void sendConfiguration() {
    configController.sendConfiguration();
  }

  public void clearLocalStorage() {
    configController.clearLocalStorage();
  }
}
