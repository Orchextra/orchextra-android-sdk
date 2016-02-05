package com.gigigo.orchextra.initalization.features;

import com.gigigo.orchextra.android.beacons.BluetoothStatus;
import com.gigigo.orchextra.domain.entities.triggers.StringValueEnum;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 2/2/16.
 */
public class BeaconFeature extends Feature {

  public BeaconFeature(StringValueEnum status) {
    super(FeatureType.BEACONS, status);
  }

  @Override public boolean isSuccess() {
    return !(getStatus() == BluetoothStatus.NO_BLTE_SUPPORTED
        || getStatus() == BluetoothStatus.NO_PERMISSIONS);
  }
}
