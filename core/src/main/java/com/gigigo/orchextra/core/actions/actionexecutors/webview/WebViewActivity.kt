package com.gigigo.orchextra.core.actions.actionexecutors.webview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gigigo.orchextra.core.R

class WebViewActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.ox_activity_web_view)
  }

  companion object Navigator {

    val EXTRA_URL = "extra_url"

    fun open(context: Context, url: String) {
      val intent = Intent(context, WebViewActivity::class.java)
      intent.putExtra(EXTRA_URL, url)
      context.startActivity(intent)
    }
  }
}
