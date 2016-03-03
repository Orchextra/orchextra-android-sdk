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

package com.gigigo.orchextra.control.controllers.action.scheduler;

import com.gigigo.orchextra.domain.abstractions.actions.ActionsSchedulerPersistor;
import com.gigigo.orchextra.domain.model.actions.ScheduledAction;
import java.util.Collections;
import java.util.List;


public class ActionsSchedulerPersistorNullImpl implements ActionsSchedulerPersistor {

  @Override public ScheduledAction getScheduledActionWithId(String id) {
    return null;
  }

  @Override public void addAction(ScheduledAction action) {
    //Fake null impl
  }

  @Override public List<ScheduledAction> obtainAllPendingActions() {
    return Collections.emptyList();
  }

  @Override public void removeAction(ScheduledAction action) {
    //Fake null impl
  }
}
