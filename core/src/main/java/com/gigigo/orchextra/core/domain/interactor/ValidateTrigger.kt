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

import android.support.annotation.WorkerThread
import com.gigigo.orchextra.core.domain.datasources.DbDataSource
import com.gigigo.orchextra.core.domain.entities.Error
import com.gigigo.orchextra.core.domain.entities.Trigger
import com.gigigo.orchextra.core.domain.entities.TriggerType.VOID
import com.gigigo.orchextra.core.domain.exceptions.DbException
import com.gigigo.orchextra.core.domain.exceptions.OxException
import com.gigigo.orchextra.core.domain.executor.PostExecutionThread
import com.gigigo.orchextra.core.domain.executor.PostExecutionThreadImp
import com.gigigo.orchextra.core.domain.executor.ThreadExecutor
import com.gigigo.orchextra.core.domain.executor.ThreadExecutorImp
import java.util.concurrent.TimeUnit

class ValidateTrigger(threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread,
    private val dbDataSource: DbDataSource) : Interactor<Trigger>(threadExecutor,
    postExecutionThread) {

  private lateinit var trigger: Trigger

  // TODO set request wait time from server
  private val waitTime: Long = TimeUnit.MINUTES.toMillis(2)

  fun validate(trigger: Trigger, onSuccess: (Trigger) -> Unit = onSuccessStub,
      onError: (OxException) -> Unit = onErrorStub) {

    this.trigger = trigger
    executeInteractor(onSuccess, onError)
  }

  override fun run() = try {
    notifySuccess(validate(trigger))
  } catch (error: DbException) {
    notifySuccess(trigger)
  } catch (e: Exception) {
    notifyError(OxException(Error.INVALID_ERROR, ""))
  }

  @WorkerThread
  fun validate(trigger: Trigger): Trigger {
    this.trigger = trigger

    val savedTrigger = dbDataSource.getTrigger(trigger.value)

    if (savedTrigger.isVoid()) {
      dbDataSource.saveTrigger(trigger)
      return trigger
    }

    dbDataSource.saveTrigger(trigger)

    return if (savedTrigger.detectedTime + waitTime < System.currentTimeMillis()) {
      trigger
    } else {
      Trigger(VOID, "")
    }
  }

  companion object Factory {
    fun create(): ValidateTrigger = ValidateTrigger(ThreadExecutorImp, PostExecutionThreadImp,
        DbDataSource.create())
  }
}