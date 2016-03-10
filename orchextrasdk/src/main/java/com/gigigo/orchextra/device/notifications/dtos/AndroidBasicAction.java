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

package com.gigigo.orchextra.device.notifications.dtos;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AndroidBasicAction implements Parcelable {

    @Expose @SerializedName("id")
    private String id;
    @Expose @SerializedName("trackId")
    private String trackId;
    @Expose @SerializedName("action")
    private String action;
    @Expose @SerializedName("url")
    private String url;
    @Expose @SerializedName("notification")
    private AndroidNotification notification;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public AndroidNotification getNotification() {
        return notification;
    }

    public void setNotification(AndroidNotification notification) {
        this.notification = notification;
    }

    @Override
    public int hashCode() {
        int megaHash = 0;
        if (this.getNotification() != null) {
            megaHash = megaHash + hashCodeObject(this.getNotification().getTitle());
            megaHash = megaHash + hashCodeObject(this.getNotification().getBody());
        }
        megaHash = megaHash + hashCodeObject(this.getAction());
        megaHash = megaHash + hashCodeObject(this.getUrl());

        return megaHash;
    }

    public static int hashCodeObject(Object o) {
        return (o == null) ? 0 : o.hashCode();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.trackId);
        dest.writeString(this.action);
        dest.writeString(this.url);
        dest.writeParcelable(this.notification, 0);
    }

    public AndroidBasicAction() {
    }

    protected AndroidBasicAction(Parcel in) {
        this.id = in.readString();
        this.trackId = in.readString();
        this.action = in.readString();
        this.url = in.readString();
        this.notification = in.readParcelable(AndroidNotification.class.getClassLoader());
    }

    public static final Creator<AndroidBasicAction> CREATOR = new Creator<AndroidBasicAction>() {
        public AndroidBasicAction createFromParcel(Parcel source) {
            return new AndroidBasicAction(source);
        }

        public AndroidBasicAction[] newArray(int size) {
            return new AndroidBasicAction[size];
        }
    };
}
