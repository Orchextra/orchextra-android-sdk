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

package com.gigigo.orchextra.control.controllers.base;

import me.panavtec.threaddecoratedview.views.ThreadSpec;
import me.panavtec.threaddecoratedview.views.ViewInjector;

public abstract class Controller<Delegate> {

  private Delegate delegate;
  private ThreadSpec mainThreadSpec;

  public Controller(ThreadSpec mainThreadSpec) {
    this.mainThreadSpec = mainThreadSpec;
  }

  public void attachDelegate(Delegate delegate) {
    this.delegate = ViewInjector.inject(delegate, mainThreadSpec);
    onDelegateAttached();
  }

  public void detachDelegate() {
    this.delegate = ViewInjector.nullObjectPatternView(delegate);
  }

  public Delegate getDelegate() {
    return delegate;
  }

  public abstract void onDelegateAttached();
}
