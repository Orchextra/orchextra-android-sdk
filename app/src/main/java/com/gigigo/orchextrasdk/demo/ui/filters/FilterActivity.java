package com.gigigo.orchextrasdk.demo.ui.filters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import com.gigigo.orchextrasdk.demo.R;
import com.gigigo.orchextrasdk.demo.ui.filters.adapter.FilterAdapter;
import com.gigigo.orchextrasdk.demo.ui.filters.entities.TriggerFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rui.alonso on 7/9/17.
 */

public class FilterActivity extends AppCompatActivity implements FilterView {

  public static final String TRIGGER_FILTERS_EXTRA = "TRIGGER_FILTERS_EXTRA";

  private FilterAdapter filterAdapter;
  private RecyclerView triggerFilterRecyclerView;
  private Button applyButton;

  private FilterPresenter filterPresenter;

  public static void open(FragmentActivity context, int requestCode) {
    Intent intent = new Intent(context, FilterActivity.class);
    context.startActivityForResult(intent, requestCode);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_filter_selection);

    filterPresenter = new FilterPresenter(this);

    triggerFilterRecyclerView = (RecyclerView) findViewById(R.id.trigger_filter_list);
    applyButton = (Button) findViewById(R.id.apply_button);

    initView();
  }

  private void initView() {
    initToolbar();
    initRecyclerView();

    applyButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        filterPresenter.applyFilters();
      }
    });

    filterPresenter.uiReady();
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
        filterPresenter.cancelFiltersEdition();
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
        filterPresenter.checkFilter(checked, position);
      }
    });
    triggerFilterRecyclerView.setAdapter(filterAdapter);
  }

  @Override public void showFilters(List<TriggerFilter> filterCollection) {
    filterAdapter.addAll(filterCollection);
  }

  @Override public void applyFilters(List<TriggerFilter> filterCollection) {
    Intent returnIntent = new Intent();
    returnIntent.putParcelableArrayListExtra(
        TRIGGER_FILTERS_EXTRA,
        (ArrayList<TriggerFilter>) filterCollection);

    setResult(Activity.RESULT_OK, returnIntent);
    finish();
  }

  @Override public void cancelFiltersEdition() {
    setResult(RESULT_CANCELED);
    finish();
  }

  @Override public void updateFilter(boolean checked, int position) {
    filterAdapter.updateItem(position, checked);
  }

  @Override public void enableApplyButton(boolean enabled) {
    applyButton.setEnabled(enabled);
  }
}
