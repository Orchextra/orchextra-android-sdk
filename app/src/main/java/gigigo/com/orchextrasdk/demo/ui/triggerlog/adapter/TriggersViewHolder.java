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

package gigigo.com.orchextrasdk.demo.ui.triggerlog.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.gigigo.orchextra.core.domain.entities.Trigger;
import gigigo.com.orchextrasdk.R;

public class TriggersViewHolder extends RecyclerView.ViewHolder {

  private TextView dateTypeTv;
  private TextView triggerTypeTv;
  private TextView triggerValueTv;

  public TriggersViewHolder(View itemView) {
    super(itemView);
    dateTypeTv = (TextView) itemView.findViewById(R.id.date_type_tv);
    triggerTypeTv = (TextView) itemView.findViewById(R.id.trigger_type_tv);
    triggerValueTv = (TextView) itemView.findViewById(R.id.trigger_value_tv);
  }

  public void render(final TriggerLog triggerLog) {

    final Trigger trigger = triggerLog.getTrigger();

    dateTypeTv.setText(getContext().getString(R.string.trigger_date_format, triggerLog.getDate()));
    triggerTypeTv.setText(
        getContext().getString(R.string.trigger_type_format, trigger.getType().name()));
    triggerValueTv.setText(
        getContext().getString(R.string.trigger_value_format, trigger.getValue()));
  }

  private Context getContext() {
    return itemView.getContext();
  }
}