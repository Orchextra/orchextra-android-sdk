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

package com.gigigo.orchextrasdk.demo.ui.triggerlog.filter.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gigigo.orchextrasdk.demo.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<FilterViewHolder> {

  private List<TriggerFilter> triggerFilterList;

  private OnItemCheckedClickListener onItemCheckedClickListener;

  public FilterAdapter() {
    this.triggerFilterList = new ArrayList<>();
  }

  @Override public FilterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.item_filter, viewGroup, false);
    return new FilterViewHolder(view);
  }

  @Override public void onBindViewHolder(FilterViewHolder viewHolder, final int position) {
    TriggerFilter triggerFilter = triggerFilterList.get(position);
    viewHolder.setOnCheckedClickListener(new FilterViewHolder.OnCheckedClickListener() {
      @Override public void OnCheckedClicked(boolean checked) {
        if(onItemCheckedClickListener != null) {
          onItemCheckedClickListener.OnItemCheckedClicked(checked, position);
        }
      }
    });
    viewHolder.render(triggerFilter);
  }

  @Override public int getItemCount() {
    return triggerFilterList.size();
  }

  public void addAll(Collection<TriggerFilter> collection) {
    triggerFilterList.clear();
    triggerFilterList.addAll(collection);
    notifyDataSetChanged();
  }

  public void addItem(int position, TriggerFilter filter) {
    triggerFilterList.add(position, filter);
    notifyItemInserted(position);
  }

  public void updateItem(int position, boolean checked) {
    TriggerFilter triggerFilter = triggerFilterList.get(position);
    triggerFilter.setActive(checked);
    triggerFilterList.set(position, triggerFilter);
    notifyItemChanged(position);
  }

  public TriggerFilter removeItem(int position) {
    final TriggerFilter filter = triggerFilterList.remove(position);
    notifyItemRemoved(position);
    return filter;
  }

  public void setOnItemCheckedClickListener(OnItemCheckedClickListener onItemCheckedClickListener) {
    this.onItemCheckedClickListener = onItemCheckedClickListener;
  }

  public interface OnItemCheckedClickListener {
    void OnItemCheckedClicked(boolean checked, int position);
  }
}