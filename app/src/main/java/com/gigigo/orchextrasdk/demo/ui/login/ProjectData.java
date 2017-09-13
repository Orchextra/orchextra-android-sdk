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

package com.gigigo.orchextrasdk.demo.ui.login;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public final class ProjectData {

  @NonNull private final String name;
  @NonNull private final String apiKey;
  @NonNull private final String apiSecret;

  public ProjectData(@NonNull String name, @NonNull String apiKey, @NonNull String apiSecret) {
    this.name = name;
    this.apiKey = apiKey;
    this.apiSecret = apiSecret;
  }

  @NonNull public String getName() {
    return name;
  }

  @NonNull public String getApiKey() {
    return apiKey;
  }

  @NonNull public String getApiSecret() {
    return apiSecret;
  }

  @NonNull public static List<ProjectData> getDefaultProjectDataList() {

    List<ProjectData> projectDataList = new ArrayList<>();

    projectDataList.add(new ProjectData("AndroidSDK", "34a4654b9804eab82aae05b2a5f949eb2a9f412c",
        "2d5bce79e3e6e9cabf6d7b040d84519197dc22f3"));

    projectDataList.add(
        new ProjectData("[Internal] Orchextra Demo", "b7be821b6b8183d4c5c203f46107abb324f7e656",
            "5c6f929395742948a013456fcee2877132907bad"));

    projectDataList.add(
        new ProjectData("[Internal] Orchextra Demo 2", "d797e0c4adcfb3de7f434991227d1e7904029130",
            "cead068e2c70000c0d7c8e5395292a77fba59d3a"));

    projectDataList.add(
        new ProjectData("Mockable", "key", "677cf75a17aeec144ee402c281ad3a732d736a8a"));

    return projectDataList;
  }

  @Nullable
  public static ProjectData getProjectDataByApiKey(@NonNull List<ProjectData> projectDataList,
      @NonNull String apiKey) {

    for (ProjectData projectData : projectDataList) {
      if (apiKey.equals(projectData.getApiKey())) {
        return projectData;
      }
    }
    return null;
  }
}
