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

package gigigo.com.orchextra.data.datasources.api.model.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public abstract class ApiProximityPoint {

    @Expose
    @SerializedName("code")
    private String code;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("tags")
    @Deprecated
    private List<String> tags;

    @Expose
    @SerializedName("type")
    private String type;

    @Expose
    @SerializedName("createdAt")
    private String createdAt;

    @Expose
    @SerializedName("updatedAt")
    private String updatedAt;

    @Expose
    @SerializedName("notifyOnExit")
    private boolean notifyOnExit;

    @Expose
    @SerializedName("notifyOnEntry")
    private boolean notifyOnEntry;

    @Expose
    @SerializedName("stayTime")
    private int stayTime;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Deprecated
    public List<String> getTags() {
        return tags;
    }

    @Deprecated
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean getNotifyOnExit() {
        return notifyOnExit;
    }

    public void setNotifyOnExit(boolean notifyOnExit) {
        this.notifyOnExit = notifyOnExit;
    }

    public boolean getNotifyOnEntry() {
        return notifyOnEntry;
    }

    public void setNotifyOnEntry(boolean notifyOnEntry) {
        this.notifyOnEntry = notifyOnEntry;
    }

    public int getStayTime() {
        return stayTime;
    }

    public void setStayTime(int stayTime) {
        this.stayTime = stayTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
