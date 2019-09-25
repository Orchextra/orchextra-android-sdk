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
import androidx.annotation.DrawableRes;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.gigigo.orchextra.core.domain.entities.Trigger;
import com.gigigo.orchextra.core.domain.entities.TriggerType;
import com.gigigo.orchextrasdk.demo.R;
import com.gigigo.orchextrasdk.demo.utils.TextUtils;

public class TriggersViewHolder extends RecyclerView.ViewHolder {

  private ImageView triggerTypeImageView;
  private TextView triggerNameTv;
  private TextView dateTypeTv;
  private TextView triggerValueTv;
  private TextView distanceTv;
  private TextView eventTv;
  private View triggerDistanceView;
  private View triggerEventView;

  public TriggersViewHolder(View itemView) {
    super(itemView);
    triggerTypeImageView = (ImageView) itemView.findViewById(R.id.trigger_type_iv);
    triggerNameTv = (TextView) itemView.findViewById(R.id.trigger_name_tv);
    dateTypeTv = (TextView) itemView.findViewById(R.id.date_type_tv);
    triggerValueTv = (TextView) itemView.findViewById(R.id.trigger_value_tv);
    distanceTv = (TextView) itemView.findViewById(R.id.trigger_distance_tv);
    eventTv = (TextView) itemView.findViewById(R.id.trigger_event_tv);
    triggerDistanceView = itemView.findViewById(R.id.trigger_distance_layout);
    triggerEventView = itemView.findViewById(R.id.trigger_event_layout);
  }

  public void render(final TriggerLog triggerLog) {

    final Trigger trigger = triggerLog.getTrigger();

    String triggerName = trigger.getType().name();
    triggerName = TextUtils.normalize(triggerName);
    triggerName = TextUtils.capitalize(triggerName);

    triggerNameTv.setText(triggerName);
    dateTypeTv.setText(getContext().getString(R.string.trigger_date_format, triggerLog.getDate()));
    triggerTypeImageView.setBackgroundResource(getTriggerImage(trigger.getType()));
    triggerValueTv.setText(trigger.getValue());

    if (trigger.getDistance() != null) {
      triggerDistanceView.setVisibility(View.VISIBLE);
      distanceTv.setText(trigger.getDistance());
    } else {
      triggerDistanceView.setVisibility(View.GONE);
    }

    if (trigger.getEvent() != null) {
      triggerEventView.setVisibility(View.VISIBLE);
      eventTv.setText(trigger.getEvent());
    } else {
      triggerEventView.setVisibility(View.GONE);
    }
  }

  @DrawableRes private int getTriggerImage(TriggerType triggerType) {
    switch (triggerType) {
      case BEACON:
        return R.drawable.ic_beacon_log;
      case BEACON_REGION:
        return R.drawable.ic_beacon_region_log;
      case EDDYSTONE:
        return R.drawable.ic_eddystone_log;
      case EDDYSTONE_REGION:
        return R.drawable.ic_beacon_region_log;
      case GEOFENCE:
        return R.drawable.ic_geofence_log;
      case QR:
        return R.drawable.ic_qr_log;
      case BARCODE:
        return R.drawable.ic_barcode_log;
      case IMAGE_RECOGNITION:
        return R.drawable.ic_image_recognition_log;
      default:
        return -1;
    }
  }

  private Context getContext() {
    return itemView.getContext();
  }
}