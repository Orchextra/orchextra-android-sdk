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

package com.gigigo.orchextra.indoorpositioning

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.gigigo.orchextra.core.domain.entities.Proximity

class IndoorPositioningReceiver : BroadcastReceiver() {

  override fun onReceive(context: Context, intent: Intent) {

    Log.d(TAG, "onReceive()")
    val config: List<Proximity> = intent.getParcelableArrayListExtra(CONFIG_EXTRA)

    IndoorPositioningService.start(context, config as ArrayList<Proximity>)
  }

  companion object {
    private val TAG = "IndoorPositioningR"
    private val CONFIG_EXTRA = "config_extra"

    fun getIndoorPositioningIntent(context: Context, config: ArrayList<Proximity>): PendingIntent {

      val alarmIntent = Intent(context, IndoorPositioningReceiver::class.java)
      alarmIntent.putParcelableArrayListExtra(CONFIG_EXTRA, config)
      return PendingIntent.getBroadcast(context, 0x43212, alarmIntent, 0)
    }
  }
}