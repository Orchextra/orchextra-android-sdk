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

package com.gigigo.orchextra.domain.interactors.themes;

import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.interactors.base.Interactor;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.interactors.error.GenericError;
import com.gigigo.orchextra.domain.model.vo.Theme;
import com.gigigo.orchextra.domain.services.themes.ThemeService;

public class ObtainThemeInteractor implements Interactor<InteractorResponse<Theme>> {

    private final ThemeService themeService;

    public ObtainThemeInteractor(ThemeService themeService) {
        this.themeService = themeService;
    }

    @Override
    public InteractorResponse<Theme> call() throws Exception {
        BusinessObject<Theme> boTheme = themeService.obtainTheme();
        if (boTheme.isSuccess()) {
            return new InteractorResponse<>(boTheme.getData());
        } else {
            return new InteractorResponse<>(new GenericError(boTheme.getBusinessError()));
        }
    }
}
