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

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.gigigo.orchextra.core.domain.entities.Trigger;
import com.gigigo.orchextrasdk.demo.R;

public class TriggersViewHolder extends RecyclerView.ViewHolder {

  private ImageView triggerTypeImageView;
  private TextView triggerNameTv;
  private TextView dateTypeTv;
  private TextView triggerTypeTv;
  private TextView triggerValueTv;
  private TextView triggerEventTv;
  private View triggerEventView;

  public TriggersViewHolder(View itemView) {
    super(itemView);
    triggerTypeImageView = (ImageView) itemView.findViewById(R.id.trigger_type_iv);
    triggerNameTv = (TextView) itemView.findViewById(R.id.trigger_name_tv);
    dateTypeTv = (TextView) itemView.findViewById(R.id.date_type_tv);
    triggerTypeTv = (TextView) itemView.findViewById(R.id.trigger_type_tv);
    triggerValueTv = (TextView) itemView.findViewById(R.id.trigger_value_tv);
    triggerEventTv = (TextView) itemView.findViewById(R.id.trigger_event_tv);

    triggerEventView = itemView.findViewById(R.id.trigger_event_layout);
  }

  public void render(final TriggerLog triggerLog) {

    final Trigger trigger = triggerLog.getTrigger();

    String triggerName = trigger.getType().name();
    triggerName = triggerName.substring(0,1).toUpperCase() + triggerName.substring(1).toLowerCase();
    triggerNameTv.setText(triggerName);
    dateTypeTv.setText(getContext().getString(R.string.trigger_date_format, triggerLog.getDate()));
    triggerTypeTv.setText(trigger.getType().name());

    @DrawableRes int imageRes;
    switch (trigger.getType()) {
      case BEACON:
        imageRes = R.drawable.ic_beacon_log;
        triggerEventView.setVisibility(View.VISIBLE);
        break;
      case BEACON_REGION:
        imageRes = R.drawable.ic_beacon_region_log;
        triggerEventView.setVisibility(View.VISIBLE);
        break;
      case EDDYSTONE:
        imageRes = R.drawable.ic_eddystone_log;
        break;
      case GEOFENCE:
        imageRes = R.drawable.ic_geofence_log;
        triggerEventView.setVisibility(View.VISIBLE);
        break;
      case QR:
        imageRes = R.drawable.ic_qr_log;
        break;
      case BARCODE:
        imageRes = R.drawable.ic_barcode_log;
        break;
      case IMAGE_RECOGNITION:
        imageRes = R.drawable.ic_image_recognition_log;
        break;
      default:
        imageRes = -1;
        break;
    }
    triggerTypeImageView.setBackgroundResource(imageRes);

    triggerValueTv.setText(trigger.getValue());
    triggerEventTv.setText(trigger.getEvent());
  }

  private Context getContext() {
    return itemView.getContext();
  }
}