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

package com.gigigo.orchextra.device.actions.stats;

import com.gigigo.orchextra.domain.abstractions.stats.StatsDispatcher;

import gigigo.com.orchextra.data.datasources.api.stats.StatsDataSourceImp;
import me.panavtec.threaddecoratedview.views.ThreadSpec;

public class StatsDispatcherImp implements StatsDispatcher{

    private final StatsDataSourceImp statsDataSourceImp;
    private final ThreadSpec threadSpec;

    public StatsDispatcherImp(StatsDataSourceImp statsDataSourceImp,
                              ThreadSpec threadSpec) {
        this.statsDataSourceImp = statsDataSourceImp;
        this.threadSpec = threadSpec;
    }

    @Override
    public void sendCompletedActionTrackId(final String trackId) {
        if (trackId != null) {
            threadSpec.execute(new Runnable() {
                @Override
                public void run() {
                    statsDataSourceImp.sendCompletedAction(trackId);
                }
            });
        }
    }
}
