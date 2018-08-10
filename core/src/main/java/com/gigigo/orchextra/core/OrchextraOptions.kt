package com.gigigo.orchextra.core


class OrchextraOptions(
    val firebaseApiKey: String = "",
    val firebaseApplicationId: String = "",
    val deviceBusinessUnits: List<String> = arrayListOf(),
    val triggeringEnabled: Boolean = false,
    val proximityEnabled: Boolean = true,
    val anonymous: Boolean = false,
    val debuggable: Boolean = false) {

  private constructor(builder: Builder) : this(
      builder.firebaseApiKey,
      builder.firebaseApplicationId,
      builder.deviceBusinessUnits,
      builder.triggeringEnabled,
      builder.proximityEnabled,
      builder.anonymous,
      builder.debuggable)

  fun hasFirebaseConfig(): Boolean = firebaseApiKey.isNotEmpty()

  class Builder {
    var firebaseApiKey: String = ""
      private set

    var firebaseApplicationId: String = ""
      private set

    var deviceBusinessUnits: List<String> = arrayListOf()
      private set

    var triggeringEnabled: Boolean = false
      private set

    var proximityEnabled: Boolean = true
      private set

    var anonymous: Boolean = false
      private set

    var debuggable: Boolean = false
      private set

    fun firebaseApiKey(firebaseApiKey: String) = apply { this.firebaseApiKey = firebaseApiKey }

    fun firebaseApplicationId(
        firebaseApplicationId: String) = apply { this.firebaseApplicationId = firebaseApplicationId }

    fun deviceBusinessUnits(
        deviceBusinessUnits: List<String>) = apply { this.deviceBusinessUnits = deviceBusinessUnits }

    fun triggeringEnabled(
        triggeringEnabled: Boolean) = apply { this.triggeringEnabled = triggeringEnabled }

    fun proximityEnabled(
        proximityEnabled: Boolean) = apply { this.proximityEnabled = proximityEnabled }

    fun anonymous(anonymous: Boolean) = apply { this.anonymous = anonymous }

    fun debuggable(debuggable: Boolean) = apply { this.debuggable = debuggable }

    fun build() = OrchextraOptions(this)
  }
}