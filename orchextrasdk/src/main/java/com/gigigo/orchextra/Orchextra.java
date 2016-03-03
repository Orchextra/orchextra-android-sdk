package com.gigigo.orchextra;

import android.app.Application;

import com.gigigo.orchextra.domain.abstractions.actions.CustomOrchextraSchemeReceiver;
import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraManagerCompletionCallback;
import com.gigigo.orchextra.sdk.OrchextraManager;

public class Orchextra {

    public static synchronized void sdkInitialize(Application application,
                                                  String apiKey,
                                                  String apiSecret,
                                                  final OrchextraCompletionCallback orchextraCompletionCallback) {

        OrchextraManager.sdkInitialize(application, apiKey, apiSecret, new OrchextraManagerCompletionCallback() {
            @Override
            public void onSuccess() {
                if (orchextraCompletionCallback != null) {
                    orchextraCompletionCallback.onSuccess();
                }
            }

            @Override
            public void onError(String s) {
                if (orchextraCompletionCallback != null) {
                    orchextraCompletionCallback.onError(s);
                }
            }
        });
    }


    public static synchronized void setCustomSchemeReceiver(final CustomSchemeReceiver customSchemeReceiver) {
        if (customSchemeReceiver != null) {
            OrchextraManager.setCustomSchemeReceiver(new CustomOrchextraSchemeReceiver() {
                @Override
                public void onReceive(String scheme) {
                    customSchemeReceiver.onReceive(scheme);
                }
            });
        }
    }

    public static synchronized void setUser(ORCUser orcUser) {
        OrchextraManager.setUser(orcUser);
    }
}
