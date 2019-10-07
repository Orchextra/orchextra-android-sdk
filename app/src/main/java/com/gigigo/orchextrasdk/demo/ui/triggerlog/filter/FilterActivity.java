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

package com.gigigo.orchextrasdk.demo.ui.triggerlog.filter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import com.gigigo.orchextra.core.domain.entities.TriggerType;
import com.gigigo.orchextrasdk.demo.R;
import com.gigigo.orchextrasdk.demo.ui.triggerlog.filter.adapter.FilterAdapter;
import com.gigigo.orchextrasdk.demo.ui.triggerlog.filter.adapter.TriggerFilter;
import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity {

  public static final int FILTER_REQUEST_CODE = 0x123;
  public static final String FILTER_DATA_EXTRA = "filter_data_extra";
  FilterAdapter filterAdapter;
  private RecyclerView triggerFilterRecyclerView;
  private Button applyButton;
  List<TriggerFilter> triggerFilterList;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_filter_selection);

    triggerFilterRecyclerView = (RecyclerView) findViewById(R.id.trigger_filter_list);
    applyButton = (Button) findViewById(R.id.apply_button);

    initView();
    loadFilters();
  }

  private void initView() {
    initToolbar();
    initRecyclerView();

    applyButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

        Intent returnIntent = new Intent();
        returnIntent.putStringArrayListExtra(FILTER_DATA_EXTRA, getFilteredTypes());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
      }
    });
  }

  private void loadFilters() {
    triggerFilterList = new ArrayList<>();
    triggerFilterList.add(new TriggerFilter(TriggerType.BARCODE));
    triggerFilterList.add(new TriggerFilter(TriggerType.QR));
    triggerFilterList.add(new TriggerFilter(TriggerType.IMAGE_RECOGNITION));
    triggerFilterList.add(new TriggerFilter(TriggerType.BEACON));
    triggerFilterList.add(new TriggerFilter(TriggerType.BEACON_REGION));
    triggerFilterList.add(new TriggerFilter(TriggerType.GEOFENCE));
    triggerFilterList.add(new TriggerFilter(TriggerType.EDDYSTONE));

    filterAdapter.addAll(triggerFilterList);
  }

  private void initToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        onBackPressed();
      }
    });
  }

  private void initRecyclerView() {
    DividerItemDecoration divider =
        new DividerItemDecoration(this, DividerItemDecoration.VERTICAL) {
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

    divider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.item_separator));

    triggerFilterRecyclerView.addItemDecoration(divider);

    triggerFilterRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    filterAdapter = new FilterAdapter();
    filterAdapter.setOnItemCheckedClickListener(new FilterAdapter.OnItemCheckedClickListener() {
      @Override public void OnItemCheckedClicked(boolean checked, int position) {
        triggerFilterList.get(position).setActive(checked);
        filterAdapter.addAll(triggerFilterList);
      }
    });
    triggerFilterRecyclerView.setAdapter(filterAdapter);
  }

  ArrayList<String> getFilteredTypes() {
    ArrayList<String> triggerTypes = new ArrayList<>();

    for (TriggerFilter triggerFilter : triggerFilterList) {
      if (triggerFilter.isActive()) {
        triggerTypes.add(triggerFilter.getType().name());
      }
    }
    return triggerTypes;
  }

  public static void openForResult(Context context, Fragment fragment) {
    Intent intent = new Intent(context, FilterActivity.class);
    fragment.startActivityForResult(intent, FILTER_REQUEST_CODE);
  }
}
