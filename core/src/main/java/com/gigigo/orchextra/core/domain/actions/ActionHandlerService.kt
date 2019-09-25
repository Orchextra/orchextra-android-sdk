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

package com.gigigo.orchextra.core.domain.actions

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log
import com.gigigo.orchextra.core.domain.entities.Action

class ActionHandlerService : IntentService(TAG) {

    override fun onHandleIntent(intent: Intent?) {

        val actionDispatcher: ActionDispatcher = ActionDispatcher.create(this)
        val action = intent?.getParcelableExtra<Action>(ACTION_EXTRA)
        if (action == null) {
            Log.d(TAG, "Action empty")
            return
        }

        Log.d(TAG, "Execute action: $action")
        actionDispatcher.executeAction(action)
    }

    companion object {
        private const val TAG = "ActionHandlerService"
        const val ACTION_EXTRA = "action_extra"
    }
}

class ActionHandlerServiceExecutor(val context: Context) {

    fun execute(action: Action) {
        val intent = Intent(context, ActionHandlerService::class.java)
        intent.putExtra(ActionHandlerService.ACTION_EXTRA, action)
        context.startService(intent)
    }

    companion object Factory {

        fun create(context: Context): ActionHandlerServiceExecutor = ActionHandlerServiceExecutor(
            context
        )
    }
}