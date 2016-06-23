/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gigigo.orchextra.control.controllers.status;

import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraStatusAccessor;
import com.gigigo.orchextra.domain.abstractions.initialization.OrchextraStatusManager;
import com.gigigo.orchextra.domain.abstractions.initialization.StartStatusType;

/**
 * Is important to know that the aim of this class is just change the status of SDK. So
 * responsibility of perform tasks related to start, init and stop are not related to this
 * class and should be performed as a result of consults to status served here. Thinking about
 * the methods below as starters or stoppers of Orchextra operation can lead the developer to big
 * misunderstanding issues, then BE CAREFUL.
 * <p/>
 * TODO Dev note: regarding refactor, think about using state pattern for managing status
 */
public class OrchextraStatusAccessorAccessorImpl implements OrchextraStatusAccessor {

    private final OrchextraStatusManager orchextraStatusManager;

    public OrchextraStatusAccessorAccessorImpl(OrchextraStatusManager orchextraStatusManager) {
        this.orchextraStatusManager = orchextraStatusManager;
    }

    @Override
    public void initialize() {
        orchextraStatusManager.setInitialized(true);
    }

    @Override
    public StartStatusType getOrchextraStatusWhenReinitMode(String apiKey, String apiSecret) throws RuntimeException {

        StartStatusType startStatusType = obtainCurrentStatusInReinitMode(apiKey, apiSecret);

        return checkOrchextraStatusInReinitMode(startStatusType, apiKey, apiSecret);
    }

    @Override
    public StartStatusType getOrchextraStatusWhenStartMode() throws RuntimeException {
        StartStatusType startStatusType = obtainCurrentStatusInStartMode();

        return checkOrchextraStatusInStartMode(startStatusType);
    }

    private StartStatusType checkOrchextraStatusInReinitMode(StartStatusType startStatusType, String apiKey, String apiSecret) {
        switch (startStatusType) {
            case SDK_WAS_ALREADY_STARTED_WITH_DIFERENT_CREDENTIALS:
                changeSdkCredentials(apiKey, apiSecret);
                return StartStatusType.SDK_WAS_ALREADY_STARTED_WITH_DIFERENT_CREDENTIALS;
            default:
                return checkOrchextraStatusInStartMode(startStatusType);
        }
    }

    private StartStatusType checkOrchextraStatusInStartMode(StartStatusType startStatusType) {
        switch (startStatusType) {
            case SDK_READY_FOR_START:
                setSDKstatusAsStarted();
                return StartStatusType.SDK_READY_FOR_START;

            case SDK_WAS_ALREADY_STARTED_WITH_SAME_CREDENTIALS:
                throw new SdkAlreadyStartedException("SDK STATUS EXCEPTION; Status: "
                        + startStatusType.getStringValue()
                        + "."
                        + " This call will be ignored");

            case SDK_WAS_NOT_INITIALIZED:
                throw new SdkNotInitializedException("SDK STATUS EXCEPTION; Status: "
                        + startStatusType.getStringValue()
                        + "."
                        + " You "
                        + "must call Orchextra.init() before calling Orchextra.start()");

            default:
                throw new SdkInitializationException("SDK STATUS EXCEPTION; Status: "
                        + startStatusType.getStringValue()
                        + "."
                        + " Review your log system");
        }
    }

    private void changeSdkCredentials(String apiKey, String apiSecret) {
        orchextraStatusManager.changeSdkCredentials(apiKey, apiSecret);
    }

    private void setSDKstatusAsStarted() {
        orchextraStatusManager.setSDKstatusAsStarted();
    }

    private StartStatusType obtainCurrentStatusInReinitMode(String apiKey, String apiSecret) {
        if (alreadyStarted()) {
            return checkStartedType(apiKey, apiSecret);
        } else {
            return obtainCurrentStatusInStartMode();
        }
    }

    private StartStatusType obtainCurrentStatusInStartMode() {
        if (!orchextraStatusManager.isInitialized()) {
            return StartStatusType.SDK_WAS_NOT_INITIALIZED;
        } else if (orchextraStatusManager.isInitialized()) {
            return StartStatusType.SDK_READY_FOR_START;
        } else {
            return StartStatusType.UNKNOWN_START_STATUS;
        }
    }

    private StartStatusType checkStartedType(String startApiKey, String startApiSecret) {
        if (orchextraStatusManager.areCredentialsEquals(startApiKey, startApiSecret)) {
            return StartStatusType.SDK_WAS_ALREADY_STARTED_WITH_SAME_CREDENTIALS;
        } else {
            return StartStatusType.SDK_WAS_ALREADY_STARTED_WITH_DIFERENT_CREDENTIALS;
        }
    }

    private boolean alreadyStarted() {
        try {
            return (orchextraStatusManager.isStarted() && hasCredentials());
        } catch (NullPointerException e) {
            return false;
        }
    }

    @Override
    public boolean hasCredentials() {
        return orchextraStatusManager.hasCredentials();
    }

    @Override
    public boolean isStarted() throws NullPointerException {
        return orchextraStatusManager.isStarted();
    }

    @Override
    public void setStoppedStatus() {
        orchextraStatusManager.setStoppedStatus();
    }

    @Override
    public void saveCredentials(String apiKey, String apiSecret) {
        changeSdkCredentials(apiKey, apiSecret);
    }
}
