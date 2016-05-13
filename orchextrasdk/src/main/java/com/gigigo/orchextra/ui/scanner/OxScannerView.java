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

package com.gigigo.orchextra.ui.scanner;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import me.dm7.barcodescanner.core.CameraPreview;
import me.dm7.barcodescanner.core.CameraUtils;

public abstract class OxScannerView extends FrameLayout implements Camera.PreviewCallback {

    protected final Context context;

    public Camera camera;
    private CameraPreview preview;

    public OxScannerView(Context context) {
        super(context);
        this.context = context;
    }

    public OxScannerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
    }


    public OxScannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public OxScannerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }

    public final void setupLayout() {
        RelativeLayout relativeLayout = new RelativeLayout(context);

        removeAllViews();
        relativeLayout.removeAllViews();

        relativeLayout.setBackgroundColor(Color.BLACK);
        relativeLayout.addView(preview);
        addView(relativeLayout);
    }

    public void startCamera(Camera camera) {
        this.camera = camera;
        if (this.camera != null) {
            preview = new CameraPreview(context, camera, this);
            setupLayout();
        }
    }

    public void startCamera() {
        startCamera(CameraUtils.getCameraInstance());
    }

    public void stopCamera() {
        if (camera != null) {
            preview.stopCameraPreview();
            preview.setCamera(null, null);
            camera.release();
            camera = null;
        }
    }
}
