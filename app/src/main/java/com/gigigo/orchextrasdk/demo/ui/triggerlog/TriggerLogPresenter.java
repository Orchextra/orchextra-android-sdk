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
import com.gigigo.orchextrasdk.demo.utils.FiltersPreferenceManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TriggerLogPresenter {

  private TriggerLogView view;
  private TriggerLogMemory triggerLogMemory;
  private FiltersPreferenceManager filtersPreferenceManager;
  private List<TriggerFilter> triggerFilterList;
  private boolean filterActivated;

  public TriggerLogPresenter(TriggerLogView view,
      FiltersPreferenceManager filtersPreferenceManager) {
    this.view = view;
    this.filtersPreferenceManager = filtersPreferenceManager;

    this.triggerLogMemory = TriggerLogMemory.getInstance();

    this.filterActivated = false;
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

  private void filterList() {
    view.cleanFilterList();

    Collection<TriggerLog> filteredList = new ArrayList<>();

    for (TriggerLog triggerLog : triggerLogMemory.getTriggerLogs()) {
      for (TriggerFilter triggerFilter : triggerFilterList) {
        if (triggerFilter.isActive() == filterActivated
            && triggerFilter.getType() == triggerLog.getTrigger().getType()) {
          filteredList.add(triggerLog);
        }
      }
    }

    view.updateFilterList((List)filteredList);
  }

  public void showFilterSelection() {
    view.showFilterSelection();
  }

  public void applyFilters() {
    this.filterActivated = true;
    loadFilters();
    filterList();
  }

  public void cancelFilterEdition() {
    this.filterActivated = false;
    cleanFilters();
    filterList();
    view.showFilterCleared();
  }

  private void loadFilters() {
    this.triggerFilterList = new ArrayList<>();
    triggerFilterList.add(
        new TriggerFilter(filtersPreferenceManager.getBarcode(), TriggerType.BARCODE));
    triggerFilterList.add(new TriggerFilter(filtersPreferenceManager.getQr(), TriggerType.QR));
    triggerFilterList.add(new TriggerFilter(filtersPreferenceManager.getImageRecognition(),
        TriggerType.IMAGE_RECOGNITION));
    triggerFilterList.add(
        new TriggerFilter(filtersPreferenceManager.getBeacon(), TriggerType.BEACON));
    triggerFilterList.add(
        new TriggerFilter(filtersPreferenceManager.getBeaconRegion(), TriggerType.BEACON_REGION));
    triggerFilterList.add(
        new TriggerFilter(filtersPreferenceManager.getGeofence(), TriggerType.GEOFENCE));
    triggerFilterList.add(
        new TriggerFilter(filtersPreferenceManager.getEddystone(), TriggerType.EDDYSTONE));
  }

  private void cleanFilters() {
    filtersPreferenceManager.saveBarcode(false);
    filtersPreferenceManager.saveQr(false);
    filtersPreferenceManager.saveImageRecognition(false);
    filtersPreferenceManager.saveBeacon(false);
    filtersPreferenceManager.saveBeaconRegion(false);
    filtersPreferenceManager.saveGeofence(false);
    filtersPreferenceManager.saveEddystone(false);

    loadFilters();
  }
}
