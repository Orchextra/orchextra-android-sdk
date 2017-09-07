/*
 * Created by Orchextra
 *
 * Copyright (C) 2017 Gigigo Mobile Services SL
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

package com.gigigo.orchextrasdk.demo;

import android.support.test.runner.AndroidJUnit4;
import com.gigigo.orchextrasdk.demo.pages.MainPage;
import com.gigigo.orchextrasdk.demo.pages.SettingsPage;
import com.gigigo.orchextrasdk.demo.utils.BaseSectionTest;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class) public class SettingsTest extends BaseSectionTest {

  @Test public void shouldSeeDefaultApiKeyAndDefaultApiSecret() {

    startActivity();

    SettingsPage settingsPage = (new MainPage(getContext())).openSettingsView();

    settingsPage.checkIfApiKeyIsEqualsTo("34a4654b9804eab82aae05b2a5f949eb2a9f412c");
    settingsPage.checkIfApiSecretIsEqualsTo("2d5bce79e3e6e9cabf6d7b040d84519197dc22f3");
  }

  @Test public void shouldStartAndStopOrchextra() {

    startActivity();

    MainPage mainPage = (new MainPage(getContext())).startOrchextra()
        .openSettingsView()
        .clickInFinishButton()
        .goBackToMainView();

    mainPage.openSettingsView();
  }
}
