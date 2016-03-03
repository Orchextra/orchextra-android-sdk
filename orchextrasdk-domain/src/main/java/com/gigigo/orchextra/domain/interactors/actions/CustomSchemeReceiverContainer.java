package com.gigigo.orchextra.domain.interactors.actions;

import com.gigigo.orchextra.domain.abstractions.actions.CustomOrchextraSchemeReceiver;

public class CustomSchemeReceiverContainer {

    private CustomOrchextraSchemeReceiver customSchemeReceiver;

    public CustomOrchextraSchemeReceiver getCustomSchemeReceiver() {
        if (customSchemeReceiver == null) {
            customSchemeReceiver = new CustomSchemeReceiverNullImpl();
        }
        return customSchemeReceiver;
    }

    public void setCustomSchemeReceiver(CustomOrchextraSchemeReceiver customSchemeReceiver) {
        this.customSchemeReceiver = customSchemeReceiver;
    }
}
