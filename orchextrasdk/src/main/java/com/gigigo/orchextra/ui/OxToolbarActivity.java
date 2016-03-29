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

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.gigigo.orchextra.R;

public abstract class OxToolbarActivity extends AppCompatActivity {

    private Toolbar oxToolbar;

    public void initViews() {
        oxToolbar = (Toolbar) findViewById(R.id.oxToolbar);

        setTheme();
        colorizeThemeIcons();
    }

    public void setTheme() {
        setSupportActionBar(oxToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getToolbarTitle());

        oxToolbar.setTitleTextColor(getResources().getColor(R.color.ox_toolbar_title_color));
        oxToolbar.setNavigationIcon(R.drawable.ox_close);
    }

    protected abstract int getToolbarTitle();

    public void colorizeThemeIcons() {
        for (int i = 0; i < oxToolbar.getChildCount(); i++) {
            final View v = oxToolbar.getChildAt(i);

            if (v instanceof ImageButton) {
                final PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(getResources().getColor(R.color.ox_toolbar_title_color), PorterDuff.Mode.MULTIPLY);
                ((ImageButton) v).getDrawable().setColorFilter(colorFilter);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
