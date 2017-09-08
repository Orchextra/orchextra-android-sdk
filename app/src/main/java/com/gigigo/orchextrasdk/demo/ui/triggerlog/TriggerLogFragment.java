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
import com.gigigo.orchextrasdk.demo.R;
import com.gigigo.orchextrasdk.demo.ui.filters.FilterActivity;
import com.gigigo.orchextrasdk.demo.ui.triggerlog.adapter.TriggerLog;
import com.gigigo.orchextrasdk.demo.ui.triggerlog.adapter.TriggersAdapter;
import com.gigigo.orchextrasdk.demo.utils.FiltersPreferenceManager;
import java.util.Collection;
import java.util.List;

public class TriggerLogFragment extends Fragment implements TriggerLogView {

  public static final int TRIGGER_REQUEST_CODE = 1;
  private static final String TAG = "TriggerLogFragment";
  private Orchextra orchextra;
  private CheckedTextView modifyFilterView;
  private Button filterCleanButton;
  private TriggersAdapter triggersAdapter;
  private RecyclerView triggerLogList;
  private View emptyListView;

  private TriggerLogPresenter triggerLogPresenter;

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
    triggerLogList = (RecyclerView) view.findViewById(R.id.trigger_log_list);
    emptyListView = view.findViewById(R.id.empty_list_view);

    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    triggerLogPresenter = new TriggerLogPresenter(this, new FiltersPreferenceManager(getActivity().getBaseContext()));

    initView();
  }

  @Override public void setupOrchextra() {
    orchextra = Orchextra.INSTANCE;
    orchextra.setErrorListener(new OrchextraErrorListener() {
      @Override public void onError(@NonNull Error error) {
        Log.e(TAG, error.toString());
        Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void initView() {
    initRecyclerView();
    initFilter();

    triggerLogPresenter.uiReady();
  }

  private void initFilter() {
    modifyFilterView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        triggerLogPresenter.showFilterSelection();
      }
    });

    filterCleanButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        triggerLogPresenter.cancelFilterEdition();
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

  @Override public void showEmptyView() {
    emptyListView.setVisibility(View.VISIBLE);
    triggerLogList.setVisibility(View.GONE);
  }

  @Override public void showData(Collection<TriggerLog> triggerLogCollection) {
    emptyListView.setVisibility(View.GONE);
    triggerLogList.setVisibility(View.VISIBLE);
    triggersAdapter.addAll(triggerLogCollection);
  }

  @Override public void showFilterCleared() {
    filterCleanButton.setVisibility(View.GONE);
    modifyFilterView.setChecked(false);
  }

  @Override public void showFilterSelection() {
    FilterActivity.open(getActivity(), TRIGGER_REQUEST_CODE);
    filterCleanButton.setVisibility(View.VISIBLE);
    modifyFilterView.setChecked(true);
  }

  @Override public void cleanFilterList() {
    triggersAdapter.clear();
  }

  @Override public void updateFilterList(List<TriggerLog> triggerLogs) {
    triggersAdapter.animateTo(triggerLogs);
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == Activity.RESULT_OK) {
      triggerLogPresenter.applyFilters();
    } else {
      triggerLogPresenter.cancelFilterEdition();
    }
  }
}
