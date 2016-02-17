package com.gigigo.orchextra.control.controllers.config;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.orchextra.control.controllers.base.Delegate;

import com.gigigo.orchextra.domain.interactors.base.InteractorError;
import me.panavtec.threaddecoratedview.views.qualifiers.ThreadDecoratedView;

@ThreadDecoratedView
public interface ConfigDelegate extends Delegate {

    void configSuccessful();

    void configError(BusinessError result);
}
