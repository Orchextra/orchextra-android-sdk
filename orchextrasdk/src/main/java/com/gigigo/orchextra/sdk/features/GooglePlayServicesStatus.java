/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
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

package com.gigigo.orchextra.sdk.features;

import com.gigigo.orchextra.domain.model.StringValueEnum;
import com.google.android.gms.common.ConnectionResult;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 2/2/16.
 */
public enum GooglePlayServicesStatus implements StringValueEnum {
  SUCCESS("Google play services initialization Successful"),
  SERVICE_MISSING("Google play services missing"), //1
  SERVICE_VERSION_UPDATE_REQUIRED("Google play services update required"), //2
  SERVICE_UPDATING("Google play services updating try again later "), //18
  SERVICE_DISABLED("Google play services disabled"),  //3
  SERVICE_INVALID("Google play services :: service invalid"); //9

  private final String type;

  GooglePlayServicesStatus(final String type) {
    this.type = type;
  }

  public String getStringValue() {
    return type;
  }

  public static GooglePlayServicesStatus getGooglePlayServicesStatus(int googleStatus){
    switch (googleStatus){
      case ConnectionResult.SUCCESS:
        return SUCCESS;
      case ConnectionResult.SERVICE_MISSING:
        return SERVICE_MISSING;
      case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
        return SERVICE_VERSION_UPDATE_REQUIRED;
      case ConnectionResult.SERVICE_UPDATING:
        return SERVICE_UPDATING;
      case ConnectionResult.SERVICE_DISABLED:
        return SERVICE_DISABLED;
      case ConnectionResult.SERVICE_INVALID:
        return SERVICE_INVALID;
      default:
        return SUCCESS;
    }
  }
}
