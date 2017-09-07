package com.gigigo.orchextrasdk.demo.ui.filters;

import com.gigigo.orchextra.core.domain.entities.TriggerType;
import com.gigigo.orchextrasdk.demo.ui.filters.entities.TriggerFilter;
import java.util.ArrayList;
import java.util.List;

public class FilterPresenter {
  List<TriggerFilter> filterCollection;
  FilterView view;

  public FilterPresenter(FilterView view) {
    this.view = view;
    filterCollection = new ArrayList<>();
    filterCollection.add(new TriggerFilter(false, TriggerType.BARCODE));
    filterCollection.add(new TriggerFilter(false, TriggerType.QR));
    filterCollection.add(new TriggerFilter(false, TriggerType.IMAGE_RECOGNITION));
    filterCollection.add(new TriggerFilter(false, TriggerType.BEACON));
    filterCollection.add(new TriggerFilter(false, TriggerType.BEACON_REGION));
    filterCollection.add(new TriggerFilter(false, TriggerType.GEOFENCE));
    filterCollection.add(new TriggerFilter(false, TriggerType.EDDYSTONE));
  }

  public void uiReady() {
    view.showFilters(filterCollection);
  }

  public void applyFilters() {
    view.applyFilters(filterCollection);
  }

  public void cancelFiltersEdition() {
    view.cancelFiltersEdition();
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
