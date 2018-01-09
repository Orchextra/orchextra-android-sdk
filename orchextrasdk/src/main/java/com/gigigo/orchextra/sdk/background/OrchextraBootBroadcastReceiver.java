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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.gigigo.orchextra.di.injector.InjectorImpl;
import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraStatusAccessor;
import com.gigigo.orchextra.sdk.OrchextraManager;
import orchextra.javax.inject.Inject;

public class OrchextraBootBroadcastReceiver extends BroadcastReceiver {

    public static final String BOOT_COMPLETED_ACTION = "android.intent.action.BOOT_COMPLETED";
    private static final String CONNECTION_READY_ACTION = "AAAAAA"; //TODO

    public static final String REFRESH_CONFIG_ACTION = "android.intent.action.REFRESH_CONFIG";
    public static final String RESTART_SERVICES =  "android.intent.action.RESTART_SERVICES";
    public static final String PAUSE_SERVICES = "android.intent.action.PAUSE_SERVICES";
    @Inject
    OrchextraStatusAccessor orchextraStatusAccessor;
    @Override
    public void onReceive(Context context, Intent intent) {
        initDependencies();
        if (orchextraStatusAccessor != null && orchextraStatusAccessor.isStarted()) {
            String action = intent.getAction();
            initTasksForBoot(context, action);
            initTasksForConnection(context, action);
        }
    }

    private void initDependencies() {
        InjectorImpl injector = OrchextraManager.getInjector();
        if (injector != null) {
            injector.injectBroadcastComponent(this);
        }
    }

    private void initTasksForBoot(Context context, String action) {
        Log.e("","startServices 8");
        if (action!=null && action.equals(BOOT_COMPLETED_ACTION)) {
            try {
                Intent pushIntent = new Intent(context, OrchextraBackgroundService.class);
                pushIntent.putExtra(action, true);
                context.startService(pushIntent);
            }
            catch (Throwable throwable){
                System.out.println("Error initTasksForBoot cannot startService on REBOOT");
                Log.e("","startServices 9"+throwable.getMessage());
            }
        }
    }

    private void initTasksForConnection(Context context, String action) {
        if (action.equals(CONNECTION_READY_ACTION)) {
            //TODO Implement neccessary tasks, maybe refresh config?
        }
    }
}
