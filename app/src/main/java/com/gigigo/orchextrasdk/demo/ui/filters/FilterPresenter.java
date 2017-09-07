package com.gigigo.orchextrasdk.demo.ui.filters;

import com.gigigo.orchextra.core.domain.entities.TriggerType;
import com.gigigo.orchextrasdk.demo.ui.filters.entities.TriggerFilter;
import com.gigigo.orchextrasdk.demo.utils.FiltersPreferenceManager;
import java.util.ArrayList;
import java.util.List;

public class FilterPresenter {
  private FiltersPreferenceManager filtersPreferenceManager;
  private List<TriggerFilter> filterCollection;
  private FilterView view;

  public FilterPresenter(FilterView view, FiltersPreferenceManager filtersPreferenceManager) {
    this.view = view;
    this.filtersPreferenceManager = filtersPreferenceManager;

    filterCollection = new ArrayList<>();
    filterCollection.add(new TriggerFilter(filtersPreferenceManager.getBarcode(), TriggerType.BARCODE));
    filterCollection.add(new TriggerFilter(filtersPreferenceManager.getQr(), TriggerType.QR));
    filterCollection.add(new TriggerFilter(filtersPreferenceManager.getImageRecognition(), TriggerType.IMAGE_RECOGNITION));
    filterCollection.add(new TriggerFilter(filtersPreferenceManager.getBeacon(), TriggerType.BEACON));
    filterCollection.add(new TriggerFilter(filtersPreferenceManager.getBeaconRegion(), TriggerType.BEACON_REGION));
    filterCollection.add(new TriggerFilter(filtersPreferenceManager.getGeofence(), TriggerType.GEOFENCE));
    filterCollection.add(new TriggerFilter(filtersPreferenceManager.getEddystone(), TriggerType.EDDYSTONE));
  }

  public void uiReady() {
    view.showFilters(filterCollection);
  }

  public void applyFilters() {
    for (TriggerFilter filter : filterCollection) {
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

    view.applyFilters(filterCollection);
  }

  public void cancelFiltersEdition() {
    view.cancelFiltersEdition(filterCollection);
  }

  public void checkFilter(boolean checked, int position) {
    TriggerFilter triggerFilter = filterCollection.get(position);
    triggerFilter.setActive(checked);
    view.updateFilter(checked, position);

    boolean enabled = false;
    for (int i = 0; !enabled && i < filterCollection.size(); i++) {
      TriggerFilter filter = filterCollection.get(i);
      if (filter.isActive()) {
        enabled = true;
      }
    }
    view.enableApplyButton(enabled);
  }
}
