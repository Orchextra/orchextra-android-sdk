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

package com.gigigo.orchextrasdk.demo.pages;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.test.espresso.ViewInteraction;
import android.view.View;
import com.gigigo.orchextrasdk.demo.R;
import com.gigigo.orchextrasdk.demo.utils.TestUtils;
import org.hamcrest.core.IsInstanceOf;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

public class SettingsPage extends BasePage {

  public SettingsPage(Context context) {
    super(context);
  }

  public MainPage goBackToMainView() {
    ViewInteraction button = onView(
        allOf(withContentDescription("Navigate up"), withParent(withId(R.id.toolbar)),
            isDisplayed()));
    button.perform(click());

    return new MainPage(context);
  }

  public DemoPage goBackToDemoView() {
    ViewInteraction button = onView(
        allOf(withContentDescription("Navigate up"), withParent(withId(R.id.toolbar)),
            isDisplayed()));
    button.perform(click());

    return new DemoPage(context);
  }

  public SettingsPage clickInFinishButton() {
    ViewInteraction button = onView(allOf(withId(R.id.finish_button), isDisplayed()));
    button.perform(click());

    return this;
  }

  public SettingsPage checkIfApiKeyIsEqualsTo(String apiKey) {
    ViewInteraction editText = onView(allOf(withId(R.id.apiKey_editText), TestUtils.childAtPosition(
        TestUtils.childAtPosition(IsInstanceOf.<View>instanceOf(TextInputLayout.class), 0), 0),
        isDisplayed()));
    editText.check(matches(withText(apiKey)));

    return this;
  }

  public SettingsPage checkIfApiSecretIsEqualsTo(String apiSecret) {
    ViewInteraction editText = onView(allOf(withId(R.id.apiSecret_editText), TestUtils.childAtPosition(
        TestUtils.childAtPosition(IsInstanceOf.<View>instanceOf(TextInputLayout.class), 0), 0),
        isDisplayed()));
    editText.check(matches(withText(apiSecret)));

    return this;
  }
}
