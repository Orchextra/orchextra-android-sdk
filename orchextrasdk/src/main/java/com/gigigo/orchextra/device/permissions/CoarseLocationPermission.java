package com.gigigo.orchextra.device.permissions;

import android.Manifest;
import com.gigigo.ggglib.permissions.Permission;
import com.gigigo.orchextra.R;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 16/1/16.
 */
public class CoarseLocationPermission implements Permission {

  //TODO Set same texts as PermissionLocationImpl

  @Override public String getAndroidPermissionStringType() {
    return  Manifest.permission.ACCESS_COARSE_LOCATION;
  }

  @Override public int getPermissionSettingsDeniedFeedback() {
    return R.string.ox_accessCoarseLocationPermissionSettingsDeniedFeedback;
  }

  @Override public int getPermissionDeniedFeedback() {
    return R.string.continueRequestPermissionDeniedFeedback;
  }

  @Override public int getPermissionRationaleTitle() {
    return R.string.ox_accessCoarseLocationPermissionRationaleTitle;
  }

  @Override public int getPermissionRationaleMessage() {
    return R.string.continueRequestPermissionRationaleMessage;
  }
}
