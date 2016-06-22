package com.gigigo.orchextra.domain.abstractions.initialization;

public interface OrchextraStatusManager {
    void setInitialized(boolean isInitialized);

    void changeSdkCredentials(String apiKey, String apiSecret);

    void setSDKstatusAsStarted();

    boolean isInitialized();

    boolean areCredentialsEquals(String startApiKey, String startApiSecret);

    boolean isStarted();

    boolean hasCredentials();

    void setStoppedStatus();
}
