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

package com.gigigo.orchextra.domain.interactors.scanner;

public enum ScannerType {
    QRCODE("QRCODE"),
    IMAGE_RECOGNITION("IR"),
    BARCODE_UPCA("UPCA"),
    BARCODE_EAN8("EAN8"),
    BARCODE_EAN13("EAN13"),
    BARCODE_CODE128("CODE128"),
    BARCODE_PDF417("PDF417"),
    BARCODE("BARCODE");

    private final String type;

    ScannerType(String type) {
        this.type = type;
    }

    public String getStringValue() {
        return type;
    }

    public static ScannerType getScannerTypeFromString(String scannerTypeString) {
        for (ScannerType scannerType : ScannerType.values()) {
            if (scannerType.getStringValue().equals(scannerTypeString)) {
                return scannerType;
            }
        }
        return BARCODE;
    }

}
