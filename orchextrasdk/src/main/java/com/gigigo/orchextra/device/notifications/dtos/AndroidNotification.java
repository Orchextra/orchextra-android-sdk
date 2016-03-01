package com.gigigo.orchextra.device.notifications.dtos;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AndroidNotification implements Parcelable {

    @Expose @SerializedName("title")
    private String title;
    @Expose @SerializedName("body")
    private String body;
    @Expose @SerializedName("shown")
    private boolean shown;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isShown() {
        return shown;
    }

    public void setShown(boolean shown) {
        this.shown = shown;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.body);
        dest.writeByte(shown ? (byte) 1 : (byte) 0);
    }

    public AndroidNotification() {
    }

    protected AndroidNotification(Parcel in) {
        this.title = in.readString();
        this.body = in.readString();
        this.shown = in.readByte() != 0;
    }

    public static final Parcelable.Creator<AndroidNotification> CREATOR = new Parcelable.Creator<AndroidNotification>() {
        public AndroidNotification createFromParcel(Parcel source) {
            return new AndroidNotification(source);
        }

        public AndroidNotification[] newArray(int size) {
            return new AndroidNotification[size];
        }
    };
}
