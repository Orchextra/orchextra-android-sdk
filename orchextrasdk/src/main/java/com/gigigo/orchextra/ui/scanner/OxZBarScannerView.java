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

import android.content.Context;
import android.hardware.Camera;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.gigigo.ggglogger.GGGLogImpl;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

import java.util.Collection;
import java.util.Iterator;

import me.dm7.barcodescanner.core.DisplayUtils;
import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;

public class OxZBarScannerView extends OxScannerView {

    private ImageScanner scanner;
    private OxZBarScannerView.ResultHandler resultHandler;

    public OxZBarScannerView(Context context) {
        super(context);
        setupScanner();
    }

    public OxZBarScannerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setupScanner();
    }

    public OxZBarScannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupScanner();
    }

    public OxZBarScannerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupScanner();
    }

    public void setResultHandler(OxZBarScannerView.ResultHandler resultHandler) {
        this.resultHandler = resultHandler;
    }

    public Collection<BarcodeFormat> getFormats() {
        return BarcodeFormat.ALL_FORMATS;
    }

    public void setupScanner() {
        scanner = new ImageScanner();

        scanner.setConfig(Symbol.NONE, Config.X_DENSITY, 3);
        scanner.setConfig(Symbol.NONE, Config.Y_DENSITY, 3);

        scanner.setConfig(Symbol.NONE, Config.ENABLE, 0);
        for (BarcodeFormat format : getFormats()) {
            scanner.setConfig(format.getId(), Config.ENABLE, 1);
        }
    }

    public void onPreviewFrame(byte[] data, Camera camera) {
        try {
            lookingForQR(data, camera);
        } catch (Exception e) {
            GGGLogImpl.log("ERROR " + e.getMessage());
        }
    }

    private boolean lookingForQR(byte[] data, Camera camera) {
        try {
            Camera.Parameters parameters = camera.getParameters();
            Camera.Size size = parameters.getPreviewSize();
            int width = size.width;
            int height = size.height;
            int result;
            if (DisplayUtils.getScreenOrientation(context) == 1) {
                byte[] barcode = new byte[data.length];

                for (result = 0; result < height; ++result) {
                    for (int syms = 0; syms < width; ++syms) {
                        barcode[syms * height + height - result - 1]
                                = data[syms + result * width];
                    }
                }

                result = width;
                width = height;
                height = result;
                data = barcode;
            }

            Image image = new Image(width, height, "Y800");
            image.setData(data);
            result = scanner.scanImage(image);
            if (result == 0) {
                image.destroy();
                image = new Image(width, height, "Y800");
                image.setData(convert2NegativeImage(data));
                result = scanner.scanImage(image);
            }


            if (result != 0) {
                stopCamera();
                if (resultHandler != null) {
                    SymbolSet symbolSet = scanner.getResults();
                    Result rawResult = new Result();
                    Iterator it = symbolSet.iterator();

                    while (it.hasNext()) {
                        Symbol sym = (Symbol) it.next();
                        String symData = sym.getData();
                        if (!TextUtils.isEmpty(symData)) {
                            rawResult.setContents(symData);
                            rawResult.setBarcodeFormat(BarcodeFormat.getFormatById(sym.getType()));
                            break;
                        }
                    }

                    resultHandler.handleResult(rawResult);
                }
            } else {
                camera.setOneShotPreviewCallback(this);
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private byte[] convert2NegativeImage(byte[] data) {
        long end = data.length;
        for (int i = 0; i < end; i++) {
            byte aux = data[i];
            data[i] = (byte) (255 - aux);
        }
        return data;
    }

    public interface ResultHandler {
        void handleResult(Result result);
    }
}
