package com.gigigo.orchextra.indoorpositioning

import android.os.Parcel
import com.gigigo.orchextra.indoorpositioning.domain.models.OxBeacon
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.*

class OxBeaconAndroidTest {

    @Test
    fun testBeaconIsParcelable() {
        val beacon = OxBeacon(
            uuid = "uuid1",
            major = 2,
            minor = 1,
            lastDetection = Date()
        )


        val parcel = Parcel.obtain()
        beacon.writeToParcel(parcel, beacon.describeContents())
        parcel.setDataPosition(0)

        beacon.testParcel()
            .apply {
                assertThat(this).isEqualTo(beacon)
                assertThat(this).isNotSameAs(beacon)
            }
    }
}

