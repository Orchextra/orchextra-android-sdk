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

package com.gigigo.orchextrasdk.demo.ui.triggerlog;

import android.support.annotation.NonNull;
import com.gigigo.orchextra.core.domain.entities.TriggerType;
import com.gigigo.orchextrasdk.demo.ui.filters.entities.TriggerFilter;
import com.gigigo.orchextrasdk.demo.ui.triggerlog.adapter.TriggerLog;
import com.gigigo.orchextrasdk.demo.ui.triggerlog.receiver.TriggerLogMemory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class TriggerLogPresenter {

  private TriggerLogView view;
  private TriggerLogMemory triggerLogMemory;

  public TriggerLogPresenter(TriggerLogView view) {
    this.view = view;

    this.triggerLogMemory = TriggerLogMemory.getInstance();
  }

  public void uiReady() {
    view.setupOrchextra();

    updateList(triggerLogMemory.getTriggerLogs());
    triggerLogMemory.setTriggerLogListener(new TriggerLogMemory.TriggerLogListener() {
      @Override public void onNewTriggerLog(TriggerLog triggerLog) {
        updateList(triggerLogMemory.getTriggerLogs());
      }
    });
  }

  private void updateList(@NonNull Collection<TriggerLog> collection) {
    if (collection.isEmpty()) {
      view.showEmptyView();
    } else {
      view.showData(collection);
    }
  }

  public void clearFilter() {
    cleanFilterList();
    view.showFilterCleared();
  }

  private void filterList(@NonNull Collection<TriggerLog> collection,
      List<TriggerType> filterTypes) {
    view.cleanFilterList();

    List<TriggerLog> collectionFiltered = new ArrayList<>();

    Iterator<TriggerLog> iterator = collection.iterator();
    while (iterator.hasNext()) {
      TriggerLog triggerLog = iterator.next();
      if (filterTypes.contains(triggerLog.getTrigger().getType())) {
        collectionFiltered.add(triggerLog);
      }
    }

    view.updateFilterList(collectionFiltered);
  }

  private void cleanFilterList() {
    view.updateFilterList(triggerLogMemory.getTriggerLogs());
  }

  public void showFilterSelection() {

    view.showFilterSelection();
  }

  public void setFilters(List<TriggerFilter> filters) {
    List<TriggerType> filterTypes = new ArrayList<>();
    for (TriggerFilter filter : filters) {
      if (filter.isActive()) {
        filterTypes.add(filter.getType());
      }
    }
    filterList(triggerLogMemory.getTriggerLogs(), filterTypes);
  }

  public void cancelFilterEdition() {

  }
}
