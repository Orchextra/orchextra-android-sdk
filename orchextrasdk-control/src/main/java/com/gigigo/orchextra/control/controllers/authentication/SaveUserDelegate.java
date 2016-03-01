package com.gigigo.orchextra.control.controllers.authentication;

import com.gigigo.orchextra.control.controllers.base.Delegate;
import com.gigigo.orchextra.domain.model.entities.authentication.Crm;

import me.panavtec.threaddecoratedview.views.qualifiers.ThreadDecoratedView;

/**
 * Created by Sergio Martinez Rodriguez
 * Date 4/12/15.
 */
@ThreadDecoratedView public interface SaveUserDelegate extends Delegate {

  void saveUserSuccessful();

  void saveUserError();

  void saveUser(Crm crm);
}
