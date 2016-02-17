package com.gigigo.orchextra.device.permissions;

import android.Manifest;
import com.gigigo.ggglib.permissions.Permission;
import com.gigigo.orchextra.R;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 16/1/16.
 */
public class CoarseLocationPermission implements Permission {

  @Override public String getAndroidPermissionStringType() {
    return  Manifest.permission.ACCESS_COARSE_LOCATION;
  }

  @Override
  public int getPermissionSettingsDeniedFeedback() {
    return R.string.ox_permission_settings;
  }

  @Override
  public int getPermissionDeniedFeedback() {
    return R.string.ox_permission_denied_geolocation;
  }

  @Override
  public int getPermissionRationaleTitle() {
    return R.string.ox_permission_rationale_title_location;
  }

  @Override
  public int getPermissionRationaleMessage() {
    return R.string.ox_permission_rationale_message_location;
  }
}
