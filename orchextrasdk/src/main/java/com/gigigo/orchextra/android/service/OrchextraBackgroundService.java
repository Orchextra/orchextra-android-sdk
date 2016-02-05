package com.gigigo.orchextra.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.gigigo.ggglogger.GGGLogImpl;
import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.android.applifecycle.AppStatusEventsListener;
import com.gigigo.orchextra.android.applifecycle.OrchextraActivityLifecycle;
import javax.inject.Inject;

public class OrchextraBackgroundService extends Service {

	@Inject
	BackgroundTasksManager backgroundTasksManager;

	@Inject OrchextraActivityLifecycle orchextraActivityLifecycle;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		GGGLogImpl.log("Service method :: onStartCommand");
		AppStatusEventsListener appStatusEventsListener = orchextraActivityLifecycle.getAppStatusEventsListener();
		appStatusEventsListener.onServiceRecreated();
		backgroundTasksManager.startBackgroundTasks();
		return START_STICKY;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Orchextra.getInjector().injectServiceComponent(this);
		GGGLogImpl.log("Service method :: onCreate");
	}

	@Override
	public void onDestroy() {
		GGGLogImpl.log("Service method :: onDestroy");
		backgroundTasksManager.finalizeBackgroundTasks();
		super.onDestroy();
	}

}