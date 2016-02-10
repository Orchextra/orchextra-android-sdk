package com.gigigo.orchextra.control.controllers.authentication;

import com.gigigo.orchextra.control.controllers.base.Delegate;
import me.panavtec.threaddecoratedview.views.qualifiers.NotDecorated;
import me.panavtec.threaddecoratedview.views.qualifiers.ThreadDecoratedView;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/12/15.
 */
@ThreadDecoratedView public interface AuthenticationDelegate extends Delegate {

  //@NotDecorated void onControllerReady();

  void authenticationSuccessful();

  void authenticationError();

  void authenticationException();

}
