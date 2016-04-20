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

package com.gigigo.orchextra.di.components;

import com.gigigo.orchextra.delegates.ConfigDelegateImp;
import com.gigigo.orchextra.di.modules.device.DelegateModule;
import com.gigigo.orchextra.di.modules.device.DelegateModuleProvider;
import com.gigigo.orchextra.di.scopes.PerDelegate;

import orchextra.dagger.Component;


@PerDelegate @Component(dependencies = OrchextraComponent.class, modules = DelegateModule.class)
public interface DelegateComponent extends DelegateModuleProvider {
  void injectConfigDelegate(ConfigDelegateImp configDelegateImp);
}
