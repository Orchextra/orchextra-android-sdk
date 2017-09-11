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

import com.gigigo.orchextra.core.domain.datasources.NetworkDataSource
import com.gigigo.orchextra.core.domain.entities.OxCRM
import com.gigigo.orchextra.core.domain.exceptions.NetworkException
import com.gigigo.orchextra.core.domain.executor.PostExecutionThread
import com.gigigo.orchextra.core.domain.executor.PostExecutionThreadImp
import com.gigigo.orchextra.core.domain.executor.ThreadExecutor
import com.gigigo.orchextra.core.domain.executor.ThreadExecutorImp

class GetCrm(private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread,
    private val networkDataSource: NetworkDataSource) : Runnable {

  private var callback: Callback? = null

  fun get(callback: Callback) {
    this.callback = callback

    threadExecutor.execute(this)
  }

  override fun run() {
    try {
      val crm = networkDataSource.getCrm()

      notifySuccess(crm)
    } catch (error: NetworkException) {
      notifyError(error)
    }
  }

  private fun notifySuccess(crm: OxCRM) {
    postExecutionThread.execute(Runnable { callback?.onSuccess(crm) })
  }

  private fun notifyError(error: NetworkException) {
    postExecutionThread.execute(Runnable { callback?.onError(error) })
  }

  interface Callback {

    fun onSuccess(crm: OxCRM)

    fun onError(error: NetworkException)
  }

  companion object Factory {

    fun create(): GetCrm = GetCrm(ThreadExecutorImp, PostExecutionThreadImp,
        NetworkDataSource.create())
  }
}