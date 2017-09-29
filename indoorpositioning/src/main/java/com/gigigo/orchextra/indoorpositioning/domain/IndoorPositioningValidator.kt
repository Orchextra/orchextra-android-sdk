package com.gigigo.orchextra.indoorpositioning.domain

import com.gigigo.orchextra.core.domain.entities.IndoorPositionConfig
import com.gigigo.orchextra.indoorpositioning.domain.models.ENTER_EVENT
import com.gigigo.orchextra.indoorpositioning.domain.models.EXIT_EVENT
import com.gigigo.orchextra.indoorpositioning.domain.models.OxBeacon
import com.gigigo.orchextra.indoorpositioning.domain.models.OxBeaconRegion
import com.gigigo.orchextra.indoorpositioning.utils.extensions.isInRegion

class IndoorPositioningValidator(private val config: List<IndoorPositionConfig>) {

  fun isValid(beacon: OxBeacon): Boolean = beacon.isInRegion(config)

  fun isValid(region: OxBeaconRegion): Boolean =
      isValid(region.beacon) && isValidEvent(region)

  private fun isValidEvent(region: OxBeaconRegion): Boolean {
    val beaconConfig = config.find { region.beacon.isInRegion(it) }

    return when (region.event) {
      ENTER_EVENT -> beaconConfig?.notifyOnEntry ?: false
      EXIT_EVENT -> beaconConfig?.notifyOnExit ?: false
      else -> false
    }
  }
}