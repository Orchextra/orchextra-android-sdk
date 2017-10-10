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
import com.gigigo.orchextra.core.Orchextra
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

class ValidateTrigger(threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread,
    private val dbDataSource: DbDataSource, private val orchextra: Orchextra) : Interactor<Trigger>(
    threadExecutor,
    postExecutionThread) {

  private lateinit var trigger: Trigger

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

    if (!trigger.isBackgroundTrigger()) {
      return trigger
    }

    val savedTrigger = dbDataSource.getTrigger(trigger.value)

    dbDataSource.saveTrigger(trigger)

    if (savedTrigger.isVoid() || trigger != savedTrigger) {
      return trigger
    }

    return if (savedTrigger.detectedTime + orchextra.waitTime < System.currentTimeMillis()) {
      trigger
    } else {
      Trigger(VOID, "")
    }
  }

  @WorkerThread
  fun isValid(trigger: Trigger): Boolean {
    return try {
      val validatedTrigger = validate(trigger)
      !validatedTrigger.isVoid()
    } catch (error: DbException) {
      true
    } catch (e: Exception) {
      false
    }
  }

  companion object Factory {
    fun create(): ValidateTrigger = ValidateTrigger(ThreadExecutorImp, PostExecutionThreadImp,
        DbDataSource.create(), Orchextra)
  }
}