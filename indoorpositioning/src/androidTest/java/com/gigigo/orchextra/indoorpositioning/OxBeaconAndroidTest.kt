package com.gigigo.orchextra.indoorpositioning

import android.os.Parcel
import android.support.test.runner.AndroidJUnit4
import com.gigigo.orchextra.indoorpositioning.domain.models.OxBeacon
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
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

