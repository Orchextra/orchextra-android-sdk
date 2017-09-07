package com.gigigo.orchextrasdk.demo.ui.triggerlog;

import com.gigigo.orchextrasdk.demo.ui.triggerlog.adapter.TriggerLog;
import java.util.Collection;
import java.util.List;

/**
 * Created by rui.alonso on 7/9/17.
 */

public interface TriggerLogView {
  void setupOrchextra();

  void showEmptyView();

  void showData(Collection<TriggerLog> triggerLogCollection);

  void updateFilterList(List<TriggerLog> triggerLogs);

  void cleanFilterList();

  void showFilterCleared();

  void showFilterSelection();
}
