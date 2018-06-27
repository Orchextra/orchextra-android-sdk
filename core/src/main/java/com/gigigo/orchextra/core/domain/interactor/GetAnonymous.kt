package com.gigigo.orchextra.core.domain.interactor

import com.gigigo.orchextra.core.domain.datasources.DbDataSource
import com.gigigo.orchextra.core.domain.exceptions.DbException
import com.gigigo.orchextra.core.domain.exceptions.OxException
import com.gigigo.orchextra.core.domain.executor.PostExecutionThread
import com.gigigo.orchextra.core.domain.executor.PostExecutionThreadImp
import com.gigigo.orchextra.core.domain.executor.ThreadExecutor
import com.gigigo.orchextra.core.domain.executor.ThreadExecutorImp

class GetAnonymous(threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread,
    private val dbDataSource: DbDataSource) : Interactor<Boolean>(threadExecutor,
    postExecutionThread) {

  fun get(onSuccess: (Boolean) -> Unit = onSuccessStub,
      onError: (OxException) -> Unit = onErrorStub) {

    executeInteractor(onSuccess, onError)
  }

  override fun run() = try {
    val anonymous = dbDataSource.getAnonymous()
    notifySuccess(anonymous)
  } catch (error: DbException) {
    notifyError(OxException(error.code, error.error))
  }

  companion object Factory {
    fun create(dbDataSource: DbDataSource): GetAnonymous =
        GetAnonymous(ThreadExecutorImp,
            PostExecutionThreadImp,
            dbDataSource)
  }

}
