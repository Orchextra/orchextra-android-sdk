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

package com.gigigo.orchextra.control.presenters.base;

public abstract class Presenter<UiView> {

  private UiView uiView;

  public Presenter() {

  }

  public void attachView(UiView view) {
    this.uiView = view;
    onViewAttached();
  }

  public void detachView() {
    this.uiView = null;
  }

  public UiView getView() {
    return uiView;
  }

  public abstract void onViewAttached();
}
