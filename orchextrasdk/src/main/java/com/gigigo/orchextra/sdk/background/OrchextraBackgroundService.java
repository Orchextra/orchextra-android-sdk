/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
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

package com.gigigo.orchextra.sdk.background;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraStatusAccessor;
import com.gigigo.orchextra.sdk.OrchextraManager;
import com.gigigo.orchextra.domain.abstractions.background.BackgroundTasksManager;
import com.gigigo.orchextra.domain.abstractions.lifecycle.AppStatusEventsListener;
import com.gigigo.orchextra.sdk.application.applifecycle.OrchextraActivityLifecycle;
import orchextra.javax.inject.Inject;

public class OrchextraBackgroundService extends Service {

	@Inject BackgroundTasksManager backgroundTasksManager;

	@Inject OrchextraActivityLifecycle orchextraActivityLifecycle;

	@Inject OrchextraStatusAccessor orchextraStatusAccessor;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		GGGLogImpl.log("Service method :: onStartCommand");
		boolean requestConfig = shouldRequestConfig(intent);
		if (orchextraStatusAccessor.isStarted()){
			startBackgroundTasks(requestConfig);
			return START_STICKY;
		}
		return START_NOT_STICKY;
	}

	private boolean shouldRequestConfig(Intent intent) {
		if (intent!=null){
			return intent.getBooleanExtra(OrchextraBootBroadcastReceiver.BOOT_COMPLETED_ACTION, false);
		}else{
			return false;
		}
	}

	private void startBackgroundTasks(boolean requestConfig) {
		AppStatusEventsListener appStatusEventsListener = orchextraActivityLifecycle.getAppStatusEventsListener();
		appStatusEventsListener.onServiceRecreated();
		backgroundTasksManager.startBackgroundTasks();

		if (requestConfig){
			backgroundTasksManager.requestConfig();
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		OrchextraManager.getInjector().injectServiceComponent(this);
		GGGLogImpl.log("Service method :: onCreate");
	}

	@Override
	public void onDestroy() {
		GGGLogImpl.log("Service method :: onDestroy");
		backgroundTasksManager.finalizeBackgroundTasks();
		super.onDestroy();
	}

}