package com.gigigo.orchextra.domain.entities.config.strategy;

import com.gigigo.orchextra.domain.entities.MethodSupported;
import com.gigigo.orchextra.domain.entities.Theme;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 17/12/15.
 */
public interface SupportsTheme extends MethodSupported {
  Theme getTheme();
}
