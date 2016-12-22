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

package com.gigigo.orchextra.device.actions;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.gigigo.orchextra.domain.abstractions.actions.ActionExecutor;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;

public class BrowserActionExecutor implements ActionExecutor {

    private final Context context;

    public BrowserActionExecutor(Context context) {
        this.context = context;
    }

    @Override
    public void execute(BasicAction action) {
        String url = action.getUrl();
        Uri uriUrl = Uri.parse(url);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uriUrl);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT );
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        Bundle b = new Bundle();
        b.putBoolean("new_window", true); //sets new window
        intent.putExtras(b);
        context.startActivity(intent);
    }
}
