package com.gigigo.orchextra.sdk.features;

import com.gigigo.orchextra.domain.abstractions.beacons.BluetoothStatus;
import com.gigigo.orchextra.domain.initalization.features.Feature;
import com.gigigo.orchextra.domain.abstractions.initialization.features.FeatureType;
import com.gigigo.orchextra.domain.model.StringValueEnum;

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
