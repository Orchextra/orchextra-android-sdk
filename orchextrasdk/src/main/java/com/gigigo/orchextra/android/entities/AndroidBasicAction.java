package com.gigigo.orchextra.android.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class AndroidBasicAction implements Parcelable {

    private String action;
    private String url;
    private AndroidNotification notification;

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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.action);
        dest.writeString(this.url);
        dest.writeParcelable(this.notification, 0);
    }

    public AndroidBasicAction() {
    }

    protected AndroidBasicAction(Parcel in) {
        this.action = in.readString();
        this.url = in.readString();
        this.notification = in.readParcelable(AndroidNotification.class.getClassLoader());
    }

    public static final Parcelable.Creator<AndroidBasicAction> CREATOR = new Parcelable.Creator<AndroidBasicAction>() {
        public AndroidBasicAction createFromParcel(Parcel source) {
            return new AndroidBasicAction(source);
        }

        public AndroidBasicAction[] newArray(int size) {
            return new AndroidBasicAction[size];
        }
    };
}
