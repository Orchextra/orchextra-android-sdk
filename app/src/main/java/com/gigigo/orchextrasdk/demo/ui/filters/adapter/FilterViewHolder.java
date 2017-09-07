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

package com.gigigo.orchextrasdk.demo.ui.filters.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckedTextView;
import com.gigigo.orchextrasdk.demo.R;
import com.gigigo.orchextrasdk.demo.ui.filters.entities.TriggerFilter;
import com.gigigo.orchextrasdk.demo.utils.TextUtils;

public class FilterViewHolder extends RecyclerView.ViewHolder {

  private CheckedTextView filterCheckText;
  private OnCheckedClickListener onCheckedClickListener;

  public FilterViewHolder(View itemView) {
    super(itemView);
    filterCheckText = (CheckedTextView) itemView.findViewById(R.id.filter_checked_text);
    filterCheckText.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if(onCheckedClickListener != null) {
          onCheckedClickListener.OnCheckedClicked(!filterCheckText.isChecked());
        }
      }
    });
  }

  public void render(final TriggerFilter triggerType) {
    String filterName = triggerType.getType().name();
    filterName = TextUtils.normalize(filterName);
    filterName = TextUtils.capitalize(filterName);
    filterCheckText.setText(filterName);
    filterCheckText.setChecked(triggerType.isActive());
  }

  public void setOnCheckedClickListener(OnCheckedClickListener onCheckedClickListener) {
    this.onCheckedClickListener = onCheckedClickListener;
  }

  public interface OnCheckedClickListener {
    void OnCheckedClicked(boolean checked);
  }
}