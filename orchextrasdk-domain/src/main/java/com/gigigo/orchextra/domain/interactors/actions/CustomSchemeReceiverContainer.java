package com.gigigo.orchextra.domain.interactors.actions;

import com.gigigo.orchextra.domain.abstractions.actions.CustomSchemeReceiver;

public class CustomSchemeReceiverContainer {

  private CustomSchemeReceiver customSchemeReceiver;

  public CustomSchemeReceiver getCustomSchemeReceiver() {
    if (customSchemeReceiver == null) {
      customSchemeReceiver = new CustomSchemeReceiverNullImpl();
    }
    return customSchemeReceiver;
  }

  public void setCustomSchemeReceiver(CustomSchemeReceiver customSchemeReceiver) {
    this.customSchemeReceiver = customSchemeReceiver;
  }
}
