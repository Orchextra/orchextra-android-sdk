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

package com.gigigo.orchextra.scanner

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gigigo.orchextra.core.domain.entities.TriggerType.QR
import com.gigigo.orchextra.core.domain.triggers.TriggerListener
import kotlinx.android.synthetic.main.activity_scanner.button

class ScannerActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_scanner)

    button.setOnClickListener { triggerListener?.onTriggerDetected(QR withValue "123") }
  }

  override fun onDestroy() {
    super.onDestroy()
    triggerListener = null
  }

  companion object Navigator {

    var triggerListener: TriggerListener? = null

    fun open(context: Context) {
      val intent = Intent(context, ScannerActivity::class.java)
      intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
      context.startActivity(intent)
    }
  }
}
