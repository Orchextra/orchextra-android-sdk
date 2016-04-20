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

package com.gigigo.orchextra.ui;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gigigo.orchextra.R;

public abstract class OxToolbarActivity extends AppCompatActivity {

    private ImageView closeToolbarButton;
    private TextView titleToolbar;

    public void initViews() {
        initUi();
        setTheme();
    }

    public void initUi() {
        closeToolbarButton = (ImageView) findViewById(R.id.closeToolbarButton);
        titleToolbar = (TextView) findViewById(R.id.titleToolbar);
    }

    public void setTheme() {
        titleToolbar.setText(getResources().getString(getToolbarTitle()));
        titleToolbar.setTextColor(getResources().getColor(R.color.ox_toolbar_title_color));
        closeToolbarButton.setImageResource(R.drawable.ox_close);
        closeToolbarButton.setOnClickListener(onCloseToolbarButtonListener);
    }

    protected abstract int getToolbarTitle();

    private View.OnClickListener onCloseToolbarButtonListener =
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
}