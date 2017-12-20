package com.gigigo.orchextrasdk.demo.test.screen

import com.agoda.kakao.KView
import com.agoda.kakao.KWebView
import com.agoda.kakao.Screen
import com.gigigo.orchextrasdk.demo.R.id

open class WebViewScreen : Screen<WebViewScreen>() {

  val toolbar: KView = KView { withId(id.ox_toolbar) }

  val webView: KWebView = KWebView { withId(id.oxWebView) }
}