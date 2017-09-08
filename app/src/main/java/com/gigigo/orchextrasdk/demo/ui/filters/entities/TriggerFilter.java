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

package com.gigigo.orchextrasdk.demo.ui.filters.entities;

import android.os.Parcel;
import android.os.Parcelable;
import com.gigigo.orchextra.core.domain.entities.TriggerType;

public class TriggerFilter implements Parcelable {
  private boolean active;
  private TriggerType type;

  public TriggerFilter(boolean active, TriggerType type) {
    this.active = active;
    this.type = type;
  }

  protected TriggerFilter(Parcel in) {
    active = in.readByte() != 0;
    type = TriggerType.valueOf(in.readString());
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeByte((byte) (active ? 1 : 0));
    dest.writeString(type.name());
  }

  @Override public int describeContents() {
    return 0;
  }

  public static final Creator<TriggerFilter> CREATOR = new Creator<TriggerFilter>() {
    @Override public TriggerFilter createFromParcel(Parcel in) {
      return new TriggerFilter(in);
    }

    @Override public TriggerFilter[] newArray(int size) {
      return new TriggerFilter[size];
    }
  };

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public TriggerType getType() {
    return type;
  }

  public void setType(TriggerType type) {
    this.type = type;
  }
}
