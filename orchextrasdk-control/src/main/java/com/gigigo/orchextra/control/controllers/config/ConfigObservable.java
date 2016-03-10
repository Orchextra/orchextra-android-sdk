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

package com.gigigo.orchextra.control.controllers.config;

import com.gigigo.orchextra.domain.abstractions.observer.Observer;
import com.gigigo.orchextra.domain.abstractions.observer.OrchextraChanges;
import java.util.ArrayList;

public class ConfigObservable implements OrchextraChanges {

  private ArrayList<Observer> observers;

  public ConfigObservable() {
    this.observers = new ArrayList<>();
  }

  @Override public void registerObserver(Observer o) {
    observers.add(o);
  }

  @Override public void removeObserver(Observer o) {
    int index = observers.indexOf(o);
    if (index >= 0) {
      observers.remove(index);
    }
  }

  @Override public void notifyObservers(Object configData) {
    for (Observer observer : observers) {
      observer.update(this, configData);
    }
  }
}
