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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gigigo.orchextrasdk.demo.BuildConfig;

import java.util.ArrayList;
import java.util.List;

public final class ProjectData {

    @NonNull
    private final String name;
    @NonNull
    private final String apiKey;
    @NonNull
    private final String apiSecret;

    public ProjectData(@NonNull String name, @NonNull String apiKey, @NonNull String apiSecret) {
        this.name = name;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getApiKey() {
        return apiKey;
    }

    @NonNull
    public String getApiSecret() {
        return apiSecret;
    }

    @NonNull
    public static List<ProjectData> getDefaultProjectDataList() {

        List<ProjectData> projectDataList = new ArrayList<>();

        if (BuildConfig.BUILD_TYPE == "release") {

            projectDataList.add(
                    new ProjectData("SHOWROOM", "fd4cf104e0a0523afb1882dc03d8514e00599c3c",
                            "1030b31c9e234dc66bba1332ec0e9d6e6836f9f0"));

            projectDataList.add(
                    new ProjectData("DEMO", "9286e35eb20e563c54cf01ba04e4528946d59402",
                            "53ccfd009c096fa07292548a6d2ef1795c1de0d4"));

            projectDataList.add(
                    new ProjectData("[UAT] WOAH MARKETS", "ef08c4dccb7649b9956296a863db002a68240be2",
                            "6bc18c500546f253699f61c11a62827679178400"));

        } else if (BuildConfig.BUILD_TYPE == "quality") {

            projectDataList.add(new ProjectData("Orchextra Demo - {{staging}}",
                    "3b1f7352079beaede1b234e798bdf05a0266a0ff", "df22059fd20c5b4b6e6465ab6b044628ea9a0726"));
        } else {

            projectDataList.add(new ProjectData("AndroidSDK", "34a4654b9804eab82aae05b2a5f949eb2a9f412c",
                    "2d5bce79e3e6e9cabf6d7b040d84519197dc22f3"));

            projectDataList.add(new ProjectData("IOS", "d4a03e91a8d86db81b6981218e5782ef52800a12",
                    "21fa16e12ef489631c0b69406943c5ec811dd767"));

            projectDataList.add(
                    new ProjectData("[Internal] Orchextra Demo", "b7be821b6b8183d4c5c203f46107abb324f7e656",
                            "5c6f929395742948a013456fcee2877132907bad"));

            projectDataList.add(
                    new ProjectData("[Internal] Orchextra Demo 2", "d797e0c4adcfb3de7f434991227d1e7904029130",
                            "cead068e2c70000c0d7c8e5395292a77fba59d3a"));

            projectDataList.add(
                    new ProjectData("ANDROID TEST", "122f51a9f80a93270dfbd61b027155936031bba9",
                            "54b0294038ae8118db6d996d4db4e082aa8447df"));

        }

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
