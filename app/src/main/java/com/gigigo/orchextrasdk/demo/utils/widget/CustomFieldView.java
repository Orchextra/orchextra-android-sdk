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

package com.gigigo.orchextrasdk.demo.utils.widget;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.gigigo.orchextra.core.domain.entities.CustomField;
import com.gigigo.orchextrasdk.demo.R;

public class CustomFieldView extends LinearLayout {

  private EditText customFieldEt;
  private TextInputLayout textInputLayout;
  private CustomField customField;

  public CustomFieldView(Context context) {
    super(context);
    init();
  }

  public CustomFieldView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public CustomFieldView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
  }

  private void init() {
    inflate(getContext(), R.layout.view_custom_field, this);
    customFieldEt = (EditText) findViewById(R.id.customFieldEt);
    textInputLayout = (TextInputLayout) findViewById(R.id.customFieldHint);
    textInputLayout.setHint("");
    customFieldEt.setText("");
  }

  public CustomField getCustomField() {
    return customField;
  }

  public void setCustomField(CustomField customField) {
    this.customField = customField;
    textInputLayout.setHint(customField.getLabel());
  }

  public String getValue() {
    return customFieldEt.getText().toString();
  }

  public void setValue(String value) {
    customFieldEt.setText(value);
  }

  public void setEnabled(boolean enabled) {
    customFieldEt.setEnabled(enabled);
  }
}