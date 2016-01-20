package com.gigigo.orchextra.android.permissions;

import android.Manifest;

import com.gigigo.ggglib.permissions.Permission;
import com.gigigo.orchextra.R;

public class PermissionLocationImp implements Permission {

    @Override
    public String getAndroidPermissionStringType() {
        return Manifest.permission.ACCESS_FINE_LOCATION;
    }

    @Override
    public int getPermissionSettingsDeniedFeedback() {
        return R.string.permission_settings;
    }

    @Override
    public int getPermissionDeniedFeedback() {
        return R.string.permission_denied_geolocation;
    }

    @Override
    public int getPermissionRationaleTitle() {
        return R.string.permission_rationale_title_location;
    }

    @Override
    public int getPermissionRationaleMessage() {
        return R.string.permission_rationale_message_location;
    }
}
