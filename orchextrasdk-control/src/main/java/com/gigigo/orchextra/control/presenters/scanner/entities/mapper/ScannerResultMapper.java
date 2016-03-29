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

package com.gigigo.orchextra.control.presenters.scanner.entities.mapper;

import com.gigigo.orchextra.control.presenters.scanner.entities.ScannerResultPresenter;
import com.gigigo.orchextra.domain.interactors.scanner.ScannerType;
import com.gigigo.orchextra.domain.model.entities.ScannerResult;
import com.gigigo.orchextra.domain.model.mappers.DataToModelMapper;

public class ScannerResultMapper implements DataToModelMapper<ScannerResult, ScannerResultPresenter> {


    @Override
    public ScannerResult mapDataToModel(ScannerResultPresenter scannerResultPresenter) {
        ScannerResult scannerResult = new ScannerResult();

        scannerResult.setContent(scannerResultPresenter.getContent());
        scannerResult.setType(ScannerType.valueOf(scannerResultPresenter.getType()));

        return scannerResult;
    }
}
