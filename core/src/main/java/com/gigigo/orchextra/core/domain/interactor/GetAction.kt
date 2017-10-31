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
import com.gigigo.orchextra.core.domain.entities.Action
import com.gigigo.orchextra.core.domain.entities.Trigger
import com.gigigo.orchextra.core.domain.exceptions.NetworkException
import com.gigigo.orchextra.core.domain.exceptions.OxException
import com.gigigo.orchextra.core.domain.executor.PostExecutionThread
import com.gigigo.orchextra.core.domain.executor.PostExecutionThreadImp
import com.gigigo.orchextra.core.domain.executor.ThreadExecutor
import com.gigigo.orchextra.core.domain.executor.ThreadExecutorImp

class GetAction constructor(threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread,
    private val networkDataSource: NetworkDataSource) : Interactor<Action>(threadExecutor,
    postExecutionThread) {

  private lateinit var trigger: Trigger

  fun get(trigger: Trigger, onSuccess: (Action) -> Unit = onSuccessStub,
      onError: (OxException) -> Unit = onErrorStub) {

    this.trigger = trigger
    executeInteractor(onSuccess, onError)
  }

  override fun run() = try {
    notifySuccess(networkDataSource.getAction(trigger))
  } catch (error: NetworkException) {
    notifyError(error)
  }

  companion object Factory {

    fun create(networkDataSource: NetworkDataSource): GetAction = GetAction(
        ThreadExecutorImp,
        PostExecutionThreadImp,
        networkDataSource)
  }
}