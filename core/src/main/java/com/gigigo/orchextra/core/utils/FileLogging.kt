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

package com.gigigo.orchextra.core.utils

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import com.gigigo.orchextra.core.Orchextra
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class FileLogging(private val context: Context) {

  fun log(priority: Int, tag: String, message: String, t: Throwable? = null) {

    val fileNameTimeStamp = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date())
    val logFile = File("sdcard/ox_log_$fileNameTimeStamp.md")
    if (!logFile.exists()) {
      try {
        logFile.createNewFile()
        writeHeader(logFile)
      } catch (e: IOException) {
        e.printStackTrace()
      }

    }

    writeLog(context, logFile, priority, tag, message)
  }

  private fun writeHeader(file: File) {
    try {
      val buf = BufferedWriter(FileWriter(file, true))
      buf.append("# Ox log")
      buf.newLine()
      buf.append("Time | Battery | Status | Priority | Tag | Log")
      buf.newLine()
      buf.append(":---:|:---:|:---:")
      buf.newLine()
      buf.close()
    } catch (e: IOException) {
      e.printStackTrace()
    }
  }

  private fun writeLog(context: Context, file: File, priority: Int, tag: String, message: String) {
    try {

      val logTimeStamp = SimpleDateFormat("E MMM dd yyyy 'at' hh:mm:ss:SSS aaa", Locale.US).format(
          Date())
      val batteryLevel = getBatteryLevel(context)
      val status = if (Orchextra.isActivityRunning()) {
        "foreground"
      } else {
        "background"
      }

      val buf = BufferedWriter(FileWriter(file, true))
      buf.append(
          "$logTimeStamp | $batteryLevel % | $status | ${getPriority(priority)} | $tag | $message")
      buf.newLine()
      buf.close()
    } catch (e: IOException) {
      e.printStackTrace()
    }
  }

  fun getBatteryLevel(context: Context): Float {
    val batteryIntent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    val level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
    val scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)

    // Error checking that probably isn't needed but I added just in case.
    return if (level == -1 || scale == -1) {
      50.0f
    } else level.toFloat() / scale.toFloat() * 100.0f

  }

  private fun getPriority(priority: Int) = when (priority) {
    2 -> "VERBOSE"
    3 -> "DEBUG"
    4 -> "INFO"
    5 -> "WARN"
    6 -> "ERROR"
    7 -> "ASSERT"
    else -> "UNKNOWN"
  }


  companion object {

    private val TAG = FileLogging::class.java.simpleName
  }
}