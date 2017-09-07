package com.gigigo.orchextrasdk.demo.ui.filters;

import com.gigigo.orchextrasdk.demo.ui.filters.entities.TriggerFilter;
import java.util.List;

/**
 * Created by rui.alonso on 7/9/17.
 */

public interface FilterView {
  void showFilters(List<TriggerFilter> filterCollection);

  void applyFilters(List<TriggerFilter> filterCollection);

  void cancelFiltersEdition(List<TriggerFilter> filterCollection);

  void updateFilter(boolean checked, int position);

  void enableApplyButton(boolean enabled);
}
