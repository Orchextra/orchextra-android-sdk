package com.gigigo.orchextra.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.android.applifecycle.AppStatusEventsListener;
import com.gigigo.orchextra.android.applifecycle.OrchextraActivityLifecycle;
import javax.inject.Inject;

public class OrchextraBackgroundService extends Service {

	private static final String TAG = "MyAppService";

	@Inject
	BackgroundTasksManager backgroundTasksManager;

	@Inject OrchextraActivityLifecycle orchextraActivityLifecycle;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "LOG :: Service method :: onStartCommand");
		AppStatusEventsListener appStatusEventsListener = orchextraActivityLifecycle.getAppStatusEventsListener();
		appStatusEventsListener.onServiceRecreated();
		backgroundTasksManager.startBackgroundTasks();
		return START_STICKY;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Orchextra.getInjector().injectServiceComponent(this);
		Log.d(TAG, "LOG :: Service method :: onCreate");
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "LOG :: Service method :: onDestroy");
		backgroundTasksManager.finalizeBackgroundTasks();
		super.onDestroy();
	}

}