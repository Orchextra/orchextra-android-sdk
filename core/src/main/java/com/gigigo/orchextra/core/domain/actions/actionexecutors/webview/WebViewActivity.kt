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

package com.gigigo.orchextra.core.domain.actions.actionexecutors.webview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.gigigo.orchextra.core.R
import kotlinx.android.synthetic.main.ox_activity_web_view.oxWebView
import java.net.URI
import java.net.URISyntaxException

class WebViewActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.ox_activity_web_view)

    initToolbar()
    oxWebView.loadUrl(getUrl())
  }

  private fun initToolbar() {

    val toolbar = findViewById(R.id.ox_toolbar) as Toolbar

    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)

    toolbar.setNavigationOnClickListener { onBackPressed() }

    title = getDomainName(getUrl())
  }

  private fun getUrl(): String {
    return intent.getStringExtra(EXTRA_URL) ?: ""
  }

  @Throws(URISyntaxException::class)
  fun getDomainName(url: String): String {
    val uri = URI(url)
    val domain = uri.host
    return if (domain.startsWith("www.")) domain.substring(4) else domain
  }

  companion object Navigator {

    val EXTRA_URL = "extra_url"

    fun open(context: Context, url: String) {
      val intent = Intent(context, WebViewActivity::class.java)
      intent.putExtra(EXTRA_URL, url)
      intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
      context.startActivity(intent)
    }
  }
}
