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

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.Toast;
import com.gigigo.orchextra.core.Orchextra;
import com.gigigo.orchextra.core.OrchextraErrorListener;
import com.gigigo.orchextra.core.domain.entities.Error;
import com.gigigo.orchextra.core.domain.entities.TriggerType;
import com.gigigo.orchextrasdk.demo.R;
import com.gigigo.orchextrasdk.demo.ui.triggerlog.adapter.TriggerLog;
import com.gigigo.orchextrasdk.demo.ui.triggerlog.adapter.TriggersAdapter;
import com.gigigo.orchextrasdk.demo.ui.triggerlog.filter.FilterActivity;
import com.gigigo.orchextrasdk.demo.ui.triggerlog.receiver.TriggerLogMemory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TriggerLogFragment extends Fragment {

  private static final String TAG = "TriggerLogFragment";
  private Orchextra orchextra;
  Set<TriggerLog> triggerLogs;
  List<TriggerType> filterTriggerTypes;
  CheckedTextView modifyFilterView;
  Button filterCleanButton;
  Button cleanButton;
  TriggersAdapter triggersAdapter;
  RecyclerView triggerLogList;
  View emptyListView;

  public TriggerLogFragment() {
  }

  public static TriggerLogFragment newInstance() {
    return new TriggerLogFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_trigger_log, container, false);

    modifyFilterView = (CheckedTextView) view.findViewById(R.id.modify_filter_button);
    filterCleanButton = (Button) view.findViewById(R.id.filter_clean_button);
    cleanButton = (Button) view.findViewById(R.id.clean_button);
    triggerLogList = (RecyclerView) view.findViewById(R.id.trigger_log_list);
    emptyListView = view.findViewById(R.id.empty_list_view);

    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    initView();
    initOrchextra();
    initTriggerLogMemory();
    updateView();
  }

  private void initOrchextra() {
    orchextra = Orchextra.INSTANCE;
    orchextra.setErrorListener(new OrchextraErrorListener() {
      @Override public void onError(@NonNull Error error) {
        Log.e(TAG, error.toString());
        Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void initTriggerLogMemory() {
    TriggerLogMemory triggerLogMemory = TriggerLogMemory.getInstance();
    triggerLogs = new HashSet<>(triggerLogMemory.getTriggerLogs());
    triggerLogs.addAll(triggerLogMemory.getTriggerLogs());
    filterTriggerTypes = new ArrayList<>();

    triggerLogMemory.setTriggerLogListener(new TriggerLogMemory.TriggerLogListener() {
      @Override public void onNewTriggerLog(TriggerLog triggerLog) {
        triggerLogs.add(triggerLog);

        if (!triggerLogs.add(triggerLog)) {
          triggerLogs.remove(triggerLog);
          triggerLogs.add(triggerLog);
        }
        updateView();
      }
    });
  }

  void updateView() {
    if (triggerLogs.isEmpty()) {

      emptyListView.setVisibility(View.VISIBLE);
      triggerLogList.setVisibility(View.GONE);
    } else {

      emptyListView.setVisibility(View.GONE);
      triggerLogList.setVisibility(View.VISIBLE);
      triggersAdapter.addAll(filter(triggerLogs, filterTriggerTypes));
    }

    if (filterTriggerTypes.isEmpty()) {
      modifyFilterView.setChecked(false);
      filterCleanButton.setVisibility(View.GONE);
    } else {
      modifyFilterView.setChecked(true);
      filterCleanButton.setVisibility(View.VISIBLE);
    }
  }

  private void initView() {
    initRecyclerView();
    initFilter();

    cleanButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        TriggerLogMemory triggerLogMemory = TriggerLogMemory.getInstance();
        triggerLogMemory.clearTriggerLogs();
        triggerLogs = new HashSet<>();
        updateView();
      }
    });
  }

  private void initFilter() {
    modifyFilterView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        FilterActivity.openForResult(getContext(), TriggerLogFragment.this);
      }
    });

    filterCleanButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        filterTriggerTypes = new ArrayList<>();
        updateView();
      }
    });
  }

  private void initRecyclerView() {
    DividerItemDecoration divider =
        new DividerItemDecoration(triggerLogList.getContext(), DividerItemDecoration.VERTICAL) {
          @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
              RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            if (position == parent.getAdapter().getItemCount() - 1) {
              outRect.setEmpty();
            } else {
              super.getItemOffsets(outRect, view, parent, state);
            }
          }
        };

    divider.setDrawable(
        ContextCompat.getDrawable(getActivity().getBaseContext(), R.drawable.item_separator));

    triggerLogList.addItemDecoration(divider);

    triggerLogList.setLayoutManager(new LinearLayoutManager(getContext()));
    triggersAdapter = new TriggersAdapter();
    triggerLogList.setAdapter(triggersAdapter);
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == FilterActivity.FILTER_REQUEST_CODE) {
      if (resultCode == Activity.RESULT_OK) {

        ArrayList<String> triggerTypes =
            data.getStringArrayListExtra(FilterActivity.FILTER_DATA_EXTRA);

        filterTriggerTypes.clear();

        for (String triggerType : triggerTypes) {
          filterTriggerTypes.add(TriggerType.valueOf(triggerType));
        }

        updateView();
      }
    }
  }

  @Override public void onDestroyView() {
    orchextra.removeErrorListener();
    super.onDestroyView();
  }

  private List<TriggerLog> filter(@NonNull Collection<TriggerLog> origin,
      @NonNull List<TriggerType> filter) {

    List<TriggerLog> triggerLogs = new ArrayList<>();

    if (filter.isEmpty()) {

      triggerLogs.addAll(origin);
      return triggerLogs;
    }

    for (TriggerLog triggerLog : origin) {
      if (filter.contains(triggerLog.getTrigger().getType())) {
        triggerLogs.add(triggerLog);
      }
    }

    return triggerLogs;
  }
}
