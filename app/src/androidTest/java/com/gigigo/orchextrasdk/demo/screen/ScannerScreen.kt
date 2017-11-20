package com.gigigo.orchextrasdk.demo.screen

import com.agoda.kakao.KButton
import com.agoda.kakao.Screen
import com.gigigo.orchextrasdk.demo.R.id

open class ScannerScreen : Screen<ScannerScreen>() {

  val oxScannerButton: KButton = KButton { withId(id.ox_scanner_button) }

}