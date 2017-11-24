package com.gigigo.orchextrasdk.demo.test.screen

import com.agoda.kakao.KButton
import com.agoda.kakao.KEditText
import com.agoda.kakao.KTextView
import com.agoda.kakao.KView
import com.agoda.kakao.Screen
import com.gigigo.orchextrasdk.demo.R
import com.gigigo.orchextrasdk.demo.R.id


open class LoginScreen : Screen<LoginScreen>() {

  val logoLayout: KView = KView { withId(R.id.logo_layout) }

  val startButton: KButton = KButton { withId(R.id.start_button) }

  val projectNameEditText: KEditText = KEditText { withId(id.projectName_editText) }

  val apiKeyEditText: KEditText = KEditText { withId(id.apiKey_editText) }

  val apiSecretEditText: KEditText = KEditText { withId(id.apiSecret_editText) }

  val errorTextView: KTextView = KTextView { withId(id.errorTextView) }


  // Scanner

  val oxScannerButton: KButton = KButton { withId(id.ox_scanner_button) }
}