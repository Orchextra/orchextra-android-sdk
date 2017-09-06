/*
 * Created by Orchextra
 *
 * Copyright (C) 2017 Gigigo Mobile Services SL
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

package com.gigigo.orchextrasdk.demo.ui.triggerlog.receiver;

import com.gigigo.orchextra.core.domain.entities.Trigger;
import com.gigigo.orchextrasdk.demo.ui.triggerlog.adapter.TriggerLog;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TriggerLogMemory {

  private static final TriggerLogMemory instance = new TriggerLogMemory();
  private TriggerLogListener triggerLogListener;
  private List<TriggerLog> triggerLogs;

  public static TriggerLogMemory getInstance() {
    return instance;
  }

  private TriggerLogMemory() {
    triggerLogs = new ArrayList<>();
  }

  public void addTrigger(Trigger trigger) {

    TriggerLog triggerLog = new TriggerLog(new Date(), trigger);

    triggerLogs.add(triggerLog);
    if (triggerLogListener != null) {
      triggerLogListener.onNewTriggerLog(triggerLog);
    }
  }

  public List<TriggerLog> getTriggerLogs() {
    return triggerLogs;
  }

  public void clearTriggerLogs() {
    triggerLogs = new ArrayList<>();
  }

  public void setTriggerLogListener(TriggerLogListener triggerLogListener) {
    this.triggerLogListener = triggerLogListener;
  }

  public interface TriggerLogListener {
    void onNewTriggerLog(TriggerLog triggerLog);
  }
}
