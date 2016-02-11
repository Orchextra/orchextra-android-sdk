package com.gigigo.orchextra.dataprovision.config.model.strategy;

import com.gigigo.orchextra.domain.model.vo.Theme;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public class RealSupportsThemeImpl implements SupportsTheme{

  private Theme theme = null;

  public RealSupportsThemeImpl(Theme theme) {
    this.theme = theme;
  }

  @Override public Theme getTheme() {
    return theme;
  }

  @Override public boolean isSupported() {
    return (theme==null)? false : true;
  }
}
