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
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.gigigo.orchextra.Orchextra;
import com.gigigo.orchextra.R;
import com.gigigo.orchextra.domain.abstractions.actions.ActionExecution;
import com.gigigo.orchextra.ui.OxToolbarActivity;

import orchextra.javax.inject.Inject;

public class OxWebViewActivity extends OxToolbarActivity {

    private static final String EXTRA_URL = "EXTRA_URL";
    private static final String EXTRA_SHOWTOOLBAR = "EXTRA_SHOWTOOLBAR";
    //this must be static,for the potential callback
    private static OxWebView orchextraWebView;

    @Inject
    ActionExecution actionExecution;

    public static void open(Context context, String url) {
        open(context, url, true);
    }

    public static void open(Context context, String url, boolean showToolBar) {
        Intent intent = new Intent(context, OxWebViewActivity.class);
        intent.putExtra(EXTRA_URL, url);
        intent.putExtra(EXTRA_SHOWTOOLBAR, showToolBar);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
//todo maybe in Orchextra
//    public static OxWebView getOxWebViewView(Context context, String url) {
//
//        OxWebView orchextraWebView = new OxWebView(context);
//        orchextraWebView.loadUrl(url);
//        return orchextraWebView;
//
//    }

    public static void navigateUrl(String url) {
        orchextraWebView.loadUrl(url);
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
        boolean showToolBar = getIntent().getBooleanExtra(EXTRA_SHOWTOOLBAR, true);
        if (!showToolBar) {
            if (Build.VERSION.SDK_INT > 17) {
                LinearLayout actionBar = (LinearLayout) findViewById(R.id.lytToolBar);

                if (actionBar != null) {
                    actionBar.setVisibility(View.GONE);
                }
            }
        }
        if (!TextUtils.isEmpty(url)) {
            orchextraWebView.loadUrl(url);
        }
    }

    private OxWebView.OnActionListener onActionListener =
            new OxWebView.OnActionListener() {
                @Override
                public void openScanner() {
                    Orchextra.startScannerActivity();
                }

                @Override
                public void openImageRecognition() {
                    Orchextra.startImageRecognition();
                }
            };
}
