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

import com.gigigo.orchextra.core.domain.datasources.DbDataSource
import com.gigigo.orchextra.core.domain.entities.Error
import com.gigigo.orchextra.core.domain.entities.Trigger
import com.gigigo.orchextra.core.domain.executor.PostExecutionThread
import com.gigigo.orchextra.core.domain.executor.PostExecutionThreadImp
import com.gigigo.orchextra.core.domain.executor.ThreadExecutor
import com.gigigo.orchextra.core.domain.executor.ThreadExecutorImp

class ValidateTrigger constructor(private val threadExecutor: ThreadExecutor,
    private val postExecutionThread: PostExecutionThread,
    private val dbDataSource: DbDataSource) : Runnable {

  private lateinit var trigger: Trigger
  private lateinit var callback: Callback

  fun validate(trigger: Trigger, callback: Callback) {
    this.trigger = trigger
    this.callback = callback

    threadExecutor.execute(this)
  }

  override fun run() {

    // get trigger from db
    // if trigger dont exist is a valid trigger and is type is beacon set enter event

    notifySuccess(trigger)
  }

  private fun notifySuccess(validTrigger: Trigger) {
    postExecutionThread.execute(Runnable { callback.onSuccess(validTrigger) })
  }

  private fun notifyError(error: Error) {
    postExecutionThread.execute(Runnable { callback.onError(error) })
  }

  interface Callback {

    fun onSuccess(validTrigger: Trigger)

    fun onError(error: Error)
  }

  companion object Factory {

    fun create(): ValidateTrigger = ValidateTrigger(ThreadExecutorImp, PostExecutionThreadImp,
        DbDataSource.create())
  }
}