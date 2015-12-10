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
