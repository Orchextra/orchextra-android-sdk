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

import com.gigigo.orchextra.core.domain.exceptions.OxException
import com.gigigo.orchextra.core.domain.executor.PostExecutionThread
import com.gigigo.orchextra.core.domain.executor.ThreadExecutor


abstract class Interactor<T>(internal val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread) : Runnable {

  internal val onSuccessStub: (T) -> Unit = {}
  internal val onErrorStub: (OxException) -> Unit = {}

  internal lateinit var onSuccess: (T) -> Unit
  internal lateinit var onError: (OxException) -> Unit


  internal fun notifySuccess(data: T) {
    postExecutionThread.execute(Runnable { onSuccess(data) })
  }

  internal fun notifyError(error: OxException) {
    postExecutionThread.execute(Runnable { onError(error) })
  }
}