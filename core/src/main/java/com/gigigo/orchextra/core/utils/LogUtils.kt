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

import android.util.Log
import com.gigigo.orchextra.core.Orchextra


object LogUtils {

  private val MAX_LOG_TAG_LENGTH = 23
  var LOG_LEVEL = Log.DEBUG
  var fileLogging: FileLogging? = null

  fun makeLogTag(str: String): String {
    return if (str.length > MAX_LOG_TAG_LENGTH) {
      str.substring(0, MAX_LOG_TAG_LENGTH - 1)
    } else {
      str
    }
  }

  /**
   * Don't use this when obfuscating class names!
   */
  fun makeLogTag(cls: Class<*>): String = makeLogTag(cls.simpleName)

  fun LOGD(tag: String, message: String) {
    if (Orchextra.isDebuggable()) {
      if (LOG_LEVEL <= Log.DEBUG) {
        Log.d(tag, message)
        fileLogging?.log(Log.DEBUG, tag, message)
      }
    }
  }

  fun LOGD(tag: String, message: String, cause: Throwable) {
    if (Orchextra.isDebuggable()) {
      if (LOG_LEVEL <= Log.DEBUG) {
        Log.d(tag, message, cause)
        fileLogging?.log(Log.DEBUG, tag, message, cause)
      }
    }
  }

  fun LOGV(tag: String, message: String) {
    if (Orchextra.isDebuggable()) {
      if (LOG_LEVEL <= Log.VERBOSE) {
        Log.v(tag, message)
        fileLogging?.log(Log.VERBOSE, tag, message)
      }
    }
  }

  fun LOGV(tag: String, message: String, cause: Throwable) {
    if (Orchextra.isDebuggable()) {
      if (LOG_LEVEL <= Log.VERBOSE) {
        Log.v(tag, message, cause)
        fileLogging?.log(Log.VERBOSE, tag, message)
      }
    }
  }

  fun LOGI(tag: String, message: String) {
    if (Orchextra.isDebuggable()) {
      Log.i(tag, message)
      fileLogging?.log(Log.INFO, tag, message)
    }
  }

  fun LOGI(tag: String, message: String, cause: Throwable) {
    if (Orchextra.isDebuggable()) {
      Log.i(tag, message, cause)
      fileLogging?.log(Log.INFO, tag, message)
    }
  }

  fun LOGW(tag: String, message: String) {
    Log.w(tag, message)
    fileLogging?.log(Log.WARN, tag, message)
  }

  fun LOGW(tag: String, message: String, cause: Throwable) {
    Log.w(tag, message, cause)
    fileLogging?.log(Log.WARN, tag, message, cause)
  }

  fun LOGE(tag: String, message: String) {
    Log.e(tag, message)
    fileLogging?.log(Log.ERROR, tag, message)
  }

  fun LOGE(tag: String, message: String, cause: Throwable) {
    Log.e(tag, message, cause)
    fileLogging?.log(Log.ERROR, tag, message, cause)
  }
}