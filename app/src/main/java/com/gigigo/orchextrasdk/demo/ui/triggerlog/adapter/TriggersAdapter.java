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

package com.gigigo.orchextrasdk.demo.ui.triggerlog.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gigigo.orchextrasdk.demo.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TriggersAdapter extends RecyclerView.Adapter<TriggersViewHolder> {

  private List<TriggerLog> triggerList;

  public TriggersAdapter() {
    this.triggerList = new ArrayList<>();
  }

  @Override public TriggersViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.item_trigger_log, viewGroup, false);
    return new TriggersViewHolder(view);
  }

  @Override public void onBindViewHolder(TriggersViewHolder viewHolder, int position) {
    TriggerLog triggerLog = triggerList.get(position);
    viewHolder.render(triggerLog);
  }

  @Override public int getItemCount() {
    return triggerList.size();
  }

  public void addAll(Collection<TriggerLog> collection) {
    triggerList.clear();
    triggerList.addAll(collection);
    notifyDataSetChanged();
  }

  public void add(TriggerLog trigger) {
    triggerList.add(trigger);
    notifyDataSetChanged();
  }

  public void animateTo(List<TriggerLog> models) {
    applyAndAnimateRemovals(models);
    applyAndAnimateAdditions(models);
    applyAndAnimateMovedItems(models);
  }

  private void applyAndAnimateRemovals(List<TriggerLog> newModels) {
    for (int i = triggerList.size() - 1; i >= 0; i--) {
      final TriggerLog model = triggerList.get(i);
      if (!newModels.contains(model)) {
        removeItem(i);
      }
    }
  }

  private void applyAndAnimateAdditions(List<TriggerLog> newModels) {
    for (int i = 0, count = newModels.size(); i < count; i++) {
      final TriggerLog model = newModels.get(i);
      if (!triggerList.contains(model)) {
        addItem(i, model);
      }
    }
  }

  private void applyAndAnimateMovedItems(List<TriggerLog> newModels) {
    for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
      final TriggerLog model = newModels.get(toPosition);
      final int fromPosition = triggerList.indexOf(model);
      if (fromPosition >= 0 && fromPosition != toPosition) {
        moveItem(fromPosition, toPosition);
      }
    }
  }

  public TriggerLog removeItem(int position) {
    final TriggerLog model = triggerList.remove(position);
    notifyItemRemoved(position);
    return model;
  }

  public void addItem(int position, TriggerLog model) {
    triggerList.add(position, model);
    notifyItemInserted(position);
  }

  public void moveItem(int fromPosition, int toPosition) {
    final TriggerLog model = triggerList.remove(fromPosition);
    triggerList.add(toPosition, model);
    notifyItemMoved(fromPosition, toPosition);
  }
}