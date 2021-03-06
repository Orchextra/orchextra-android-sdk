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

package com.gigigo.orchextra.core.domain.interactor

import android.util.Log
import com.gigigo.orchextra.core.domain.datasources.DbDataSource
import com.gigigo.orchextra.core.domain.datasources.NetworkDataSource
import com.gigigo.orchextra.core.domain.entities.Configuration
import com.gigigo.orchextra.core.domain.exceptions.DbException
import com.gigigo.orchextra.core.domain.exceptions.NetworkException
import com.gigigo.orchextra.core.domain.exceptions.OxException
import com.gigigo.orchextra.core.domain.executor.PostExecutionThread
import com.gigigo.orchextra.core.domain.executor.PostExecutionThreadImp
import com.gigigo.orchextra.core.domain.executor.ThreadExecutor
import com.gigigo.orchextra.core.domain.executor.ThreadExecutorImp
import java.util.concurrent.TimeUnit

class GetConfiguration(threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread,
    private val networkDataSource: NetworkDataSource,
    private val dbDataSource: DbDataSource) : Interactor<Configuration>(threadExecutor,
    postExecutionThread) {

  private lateinit var apiKey: String

  fun get(apiKey: String, onSuccess: (Configuration) -> Unit = onSuccessStub,
      onError: (OxException) -> Unit = onErrorStub) {

    this.apiKey = apiKey
    executeInteractor(onSuccess, onError)
  }

  override fun run() = try {
    val configuration = networkDataSource.getConfiguration(apiKey)

    try {
      dbDataSource.saveWaitTime(TimeUnit.SECONDS.toMillis(configuration.requestWaitTime))
      dbDataSource.saveConfiguration(configuration)
    } catch (e: DbException) {
      Log.e(TAG, "GetConfiguration", e)
    }

    notifySuccess(configuration)

  } catch (error: NetworkException) {
    try {
      val configuration = dbDataSource.getConfiguration()
      notifySuccess(configuration)

    } catch (dbError: DbException) {
      notifyError(error)
    }
  }

  companion object Factory {

    private const val TAG = "GetConfiguration"

    fun create(networkDataSource: NetworkDataSource, dbDataSource: DbDataSource):
        GetConfiguration = GetConfiguration(
        ThreadExecutorImp,
        PostExecutionThreadImp,
        networkDataSource,
        dbDataSource)
  }
}