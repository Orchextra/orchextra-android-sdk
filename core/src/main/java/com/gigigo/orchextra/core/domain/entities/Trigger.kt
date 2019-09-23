/*
 * Created by Orchextra
 *
 * Copyright (C) 2017 Gigigo Mobile Services SL
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

package com.gigigo.orchextra.core.domain.entities

import android.os.Parcelable
import com.gigigo.orchextra.core.domain.entities.TriggerType.BEACON
import com.gigigo.orchextra.core.domain.entities.TriggerType.BEACON_REGION
import com.gigigo.orchextra.core.domain.entities.TriggerType.EDDYSTONE
import com.gigigo.orchextra.core.domain.entities.TriggerType.EDDYSTONE_REGION
import com.gigigo.orchextra.core.domain.entities.TriggerType.GEOFENCE
import com.gigigo.orchextra.core.domain.entities.TriggerType.VOID
import kotlinx.android.parcel.Parcelize


enum class TriggerType {
    BEACON,
    BEACON_REGION,
    EDDYSTONE,
    EDDYSTONE_REGION,
    GEOFENCE,
    QR,
    BARCODE,
    IMAGE_RECOGNITION,
    VOID;

    infix fun withValue(value: String) = Trigger(this, value)
}

@Parcelize
data class Trigger constructor(
    val type: TriggerType,
    val value: String,
    val lat: Double? = null,
    val lng: Double? = null,
    val event: String? = null,
    val phoneStatus: String? = null,
    val distance: String? = null,
    val temperature: Float? = null,
    val battery: Long? = null,
    val uptime: Long? = null,
    val detectedTime: Long = System.currentTimeMillis()
) : Parcelable {

    fun isVoid(): Boolean = type == VOID

    fun isBackgroundTrigger(): Boolean = (type == BEACON
            || type == BEACON_REGION
            || type == EDDYSTONE
            || type == EDDYSTONE_REGION
            || type == GEOFENCE)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        (other as? Trigger).let {
            it ?: return false
            if (type != it.type) return false
            if (value != it.value) return false
            if (event != it.event) return false
            if (distance != it.distance) return false
        }
        return true
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}