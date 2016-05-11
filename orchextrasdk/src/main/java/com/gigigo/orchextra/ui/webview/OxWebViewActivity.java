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

package com.gigigo.orchextra.ui.webview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.gigigo.orchextra.R;
import com.gigigo.orchextra.domain.abstractions.actions.ActionExecution;
import com.gigigo.orchextra.ui.OxToolbarActivity;

import orchextra.javax.inject.Inject;

public class OxWebViewActivity extends OxToolbarActivity {

    private static final String EXTRA_URL = "EXTRA_URL";

    private OxWebView orchextraWebView;

    @Inject
    ActionExecution actionExecution;

    public static void open(Context context, String url) {
        Intent intent = new Intent(context, OxWebViewActivity.class);
        intent.putExtra(EXTRA_URL, url);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ox_activity_webview_layout);

        initMainViews();
        initWebView();
    }

    public void initMainViews() {
        super.initMainViews();
        orchextraWebView = (OxWebView) findViewById(R.id.ox_orchextraWebView);
    }

    @Override
    protected int getToolbarTitle() {
        return R.string.ox_webview_toolbar_title;
    }

    private void initWebView() {
        orchextraWebView.setOnActionListener(onActionListener);

        String url = getIntent().getStringExtra(EXTRA_URL);

        if (!TextUtils.isEmpty(url)) {
            orchextraWebView.loadUrl(url);
        }
    }

    private OxWebView.OnActionListener onActionListener =
            new OxWebView.OnActionListener() {
                @Override
                public void openScanner() {
                    //TODO Open Scanner
                }

                @Override
                public void openImageRecognition() {
                    //TODO Open Image Recognition
                }
            };
}
