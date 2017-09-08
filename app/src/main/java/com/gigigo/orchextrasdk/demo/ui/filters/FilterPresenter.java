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

package com.gigigo.orchextrasdk.demo.ui.filters;

import com.gigigo.orchextra.core.domain.entities.TriggerType;
import com.gigigo.orchextrasdk.demo.ui.filters.entities.TriggerFilter;
import com.gigigo.orchextrasdk.demo.utils.FiltersPreferenceManager;
import java.util.ArrayList;
import java.util.List;

public class FilterPresenter {
  private FiltersPreferenceManager filtersPreferenceManager;
  private List<TriggerFilter> triggerFilterList;
  private FilterView view;

  public FilterPresenter(FilterView view, FiltersPreferenceManager filtersPreferenceManager) {
    this.view = view;
    this.filtersPreferenceManager = filtersPreferenceManager;

    loadFilters();
  }

  private void loadFilters() {
    triggerFilterList = new ArrayList<>();
    triggerFilterList.add(new TriggerFilter(filtersPreferenceManager.getBarcode(), TriggerType.BARCODE));
    triggerFilterList.add(new TriggerFilter(filtersPreferenceManager.getQr(), TriggerType.QR));
    triggerFilterList.add(new TriggerFilter(filtersPreferenceManager.getImageRecognition(), TriggerType.IMAGE_RECOGNITION));
    triggerFilterList.add(new TriggerFilter(filtersPreferenceManager.getBeacon(), TriggerType.BEACON));
    triggerFilterList.add(new TriggerFilter(filtersPreferenceManager.getBeaconRegion(), TriggerType.BEACON_REGION));
    triggerFilterList.add(new TriggerFilter(filtersPreferenceManager.getGeofence(), TriggerType.GEOFENCE));
    triggerFilterList.add(new TriggerFilter(filtersPreferenceManager.getEddystone(), TriggerType.EDDYSTONE));
  }

  public void uiReady() {
    view.showFilters(triggerFilterList);
  }

  public void applyFilters() {
    for (TriggerFilter filter : triggerFilterList) {
      switch (filter.getType()){
        case BEACON:
          filtersPreferenceManager.saveBeacon(filter.isActive());
          break;
        case BEACON_REGION:
          filtersPreferenceManager.saveBeaconRegion(filter.isActive());
          break;
        case EDDYSTONE:
          filtersPreferenceManager.saveEddystone(filter.isActive());
          break;
        case GEOFENCE:
          filtersPreferenceManager.saveGeofence(filter.isActive());
          break;
        case QR:
          filtersPreferenceManager.saveQr(filter.isActive());
          break;
        case BARCODE:
          filtersPreferenceManager.saveBarcode(filter.isActive());
          break;
        case IMAGE_RECOGNITION:
          filtersPreferenceManager.saveImageRecognition(filter.isActive());
          break;
        default:
          break;
      }
    }

    view.applyFilters();
  }

  public void cancelFiltersEdition() {
    view.cancelFiltersEdition();
  }

  public void checkFilter(boolean checked, int position) {
    TriggerFilter triggerFilter = triggerFilterList.get(position);
    triggerFilter.setActive(checked);
    view.updateFilter(checked, position);
  }
}
