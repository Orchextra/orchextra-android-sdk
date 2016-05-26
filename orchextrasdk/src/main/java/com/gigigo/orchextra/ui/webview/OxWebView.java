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

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.gigigo.ggglib.device.AndroidSdkVersion;
import com.gigigo.orchextra.R;

public class OxWebView extends LinearLayout {

    public static final String OPEN_SCANNER = "OpenScanner";
    public static final String OPEN_IMAGE_RECOGNITION = "OpenImageRecog";
    public static final String CLOSE_SCANNER = "CloseScanner";
    public static final String CLOSE_IMAGE_RECOGNITION = "CloseImageRecog";
    public static final String CLOSE_UNKNOW = "CLOSE_UNKNOW";

    private WebView webView;

    private View progress;

    private final Context context;

    private OnActionListener onActionListener;

    public OxWebView(Context context) {
        super(context);
        this.context = context;

        init();
    }

    public OxWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public OxWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public OxWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;

        init();
    }

    private void init() {
        initView();
        settingWebView();
    }

    private void initView() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.ox_webview_layout, this, true);
        webView = (WebView) view.findViewById(R.id.ox_webView);
        progress = view.findViewById(R.id.ox_progress);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void settingWebView() {
        webView.setClickable(true);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        if (AndroidSdkVersion.hasJellyBean16()) {
            webView.getSettings().setAllowFileAccessFromFileURLs(true);
            webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }

        webView.getSettings().setGeolocationDatabasePath(getContext().getFilesDir().getPath());
        webView.setWebChromeClient(new WebChromeClient() {
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                showProgressView(false);
            }
        });

        webView.addJavascriptInterface(new PostMessage(), "OrchextraJSNativeAndroid");
    }

    public void loadUrl(String url) {
        showProgressView(true);

        webView.loadUrl(url);
    }

    private void showProgressView(boolean visible) {
        progress.setVisibility(visible ? VISIBLE : GONE);
    }

    public class PostMessage {

        @JavascriptInterface
        public void postMessage(String nativeMessage) {
            if (nativeMessage != null) {
                String callBack = CLOSE_UNKNOW;
                if (nativeMessage.equals(OPEN_SCANNER)) {
                    if (onActionListener != null) {
                        onActionListener.openScanner();
                    }
//                  Orchextra.openScannerView();
                    callBack = CLOSE_SCANNER;
                } else if (nativeMessage.equals(OPEN_IMAGE_RECOGNITION)) {
//                  OrchextraManager.startImageRecognitionActivity();
                    callBack = CLOSE_IMAGE_RECOGNITION;
                }
//                Orchextra.setNativeResponseToJSCallBack(callBack);
            }

        }
    }

    public interface OnActionListener {
        void openScanner();
        void openImageRecognition();
    }

    public void setOnActionListener(OnActionListener onActionListener) {
        this.onActionListener = onActionListener;
    }
}
