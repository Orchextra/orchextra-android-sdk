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

package com.gigigo.orchextra.domain.model.actions.strategy;

import com.gigigo.orchextra.domain.interactors.actions.ActionDispatcher;
import com.gigigo.orchextra.domain.model.actions.ActionType;
import com.gigigo.orchextra.domain.model.actions.ScheduledAction;
import com.gigigo.orchextra.domain.model.actions.types.BrowserAction;
import com.gigigo.orchextra.domain.model.actions.types.CustomAction;
import com.gigigo.orchextra.domain.model.actions.types.EmptyAction;
import com.gigigo.orchextra.domain.model.actions.types.NotificationAction;
import com.gigigo.orchextra.domain.model.actions.types.NotificationPushAction;
import com.gigigo.orchextra.domain.model.actions.types.ScanAction;
import com.gigigo.orchextra.domain.model.actions.types.VuforiaScanAction;
import com.gigigo.orchextra.domain.model.actions.types.WebViewAction;

/**
 * TODO Dev note: regarding refactor, think about using decorator and Abstract factory instead
 * of using strategy and Builder patterns
 */
public abstract class BasicAction {

    private String id;
    private String trackId;
    protected ActionType actionType;
    protected URLBehavior urlBehavior;
    protected NotificationBehavior notificationBehavior;
    protected ScheduleBehavior scheduleBehavior;
    protected boolean hasNotification = false;


    private String eventCode;

    public BasicAction(String id, String trackId, String url, OrchextraNotification notification,
                       Schedule schedule) {

        this.id = id;
        this.trackId = trackId;

        this.urlBehavior = new URLBehaviorImpl(url);

        if (notification == null) {
            this.notificationBehavior = new EmptyNotificationBehaviorImpl();
            hasNotification = false;
        } else {
            this.notificationBehavior = new NotificationBehaviorImpl(notification);
            hasNotification = true;
        }

        if (schedule == null) {
            this.scheduleBehavior = new EmptyScheduleBehaviorImpl();
        } else {
            this.scheduleBehavior = new ScheduleBehaviorImpl(schedule);
        }


    }

    //todo get this values from resources or calculate that
    private final String strfakenotificationtitle = "Orchextra";
    private final String strfakenotificationbody = "Click to open your App";

    public void createFakeNotification() {
        OrchextraNotification fakeNotification = new OrchextraNotification();
        fakeNotification.setTitle(strfakenotificationtitle);
        fakeNotification.setBody(strfakenotificationbody);
        fakeNotification.setShown(false);
        this.notificationBehavior = new NotificationBehaviorImpl(fakeNotification);
    }

    public boolean getHasNotification() {
        return hasNotification;
    }

    public boolean getisFakeNotification() {
        if (getHasNotification()) {
            if (this.getNotificationBehavior() != null
                    && this.getNotificationBehavior().getTitle().equals(strfakenotificationtitle)) {
                return true;
            }
            return false;
        }
            return false;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public String getId() {
        return id;
    }

    public String getTrackId() {
        return trackId;
    }

    public void performAction(ActionDispatcher actionDispatcher) {
        if (this.getisFakeNotification()) {
            //this now is only for avoid fake notification7need by app when is on background on ForegroundNotificationBuilderImpl.java buildNotification
            notificationBehavior.getNotification().setShown(true);
        }
        if (notificationBehavior.isSupported()) {
            performNotifAction(actionDispatcher);
        } else {
            performSimpleAction(actionDispatcher);
        }
    }

    public boolean isScheduled() {
        return scheduleBehavior.isSupported();
    }

    public ScheduledActionImpl getScheduledAction() {
        if (scheduleBehavior.isSupported()) {
            return new ScheduledActionImpl(this);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public String getUrl() {
        return this.urlBehavior.getUrl();
    }

    public OrchextraNotification getNotificationBehavior() {
        return notificationBehavior.getNotification();
    }

    protected abstract void performSimpleAction(ActionDispatcher actionDispatcher);

    protected abstract void performNotifAction(ActionDispatcher actionDispatcher);

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public static ScheduledAction generateCancelActionHolder(String id, boolean cancelable) {
        BasicAction action = new BasicAction.ActionBuilder().actionId(id)
                .cancelable(cancelable)
                .actionType(ActionType.NOT_DEFINED)
                .build();
        return action.getScheduledAction();
    }

    public static class ActionBuilder {

        private String id;
        private String trackId;
        private ActionType actionType;
        private String url;
        private OrchextraNotification notification;
        private Schedule schedule;

        public ActionBuilder() {
        }

        public ActionBuilder(String id, String tid, ActionType actionType, String url,
                             OrchextraNotification notification, Schedule schedule) {
            this(id, tid, actionType, url, notification);
            this.schedule = schedule;
        }

        public ActionBuilder(String id, String tid, ActionType actionType, String url,
                             OrchextraNotification notification) {

            this.id = id;
            this.trackId = tid;
            this.actionType = actionType;
            this.url = url;
            this.notification = notification;
        }

        public ActionBuilder actionId(String id) {
            this.id = id;
            return this;
        }

        public ActionBuilder actionType(ActionType actionType) {
            this.actionType = actionType;
            return this;
        }

        public ActionBuilder cancelable(boolean cancelable) {
            this.schedule = new Schedule(cancelable, 0);
            return this;
        }

        public BasicAction build() {
            switch (actionType) {
                case BROWSER:
                    return new BrowserAction(id, trackId, url, notification, schedule);
                case WEBVIEW:
                    return new WebViewAction(id, trackId, url, notification, schedule);
                case SCAN:
                    return new ScanAction(id, trackId, url, notification, schedule);
                case VUFORIA_SCAN:
                    return new VuforiaScanAction(id, trackId, url, notification, schedule);
                case CUSTOM_SCHEME:
                    return new CustomAction(id, trackId, url, notification, schedule);
                case NOTIFICATION:
                    return new NotificationAction(id, trackId, url, notification, schedule);
                case NOTIFICATION_PUSH:
                    return new NotificationPushAction(id, trackId, url, notification, schedule);
                case NOT_DEFINED:
                default:
                    return new EmptyAction(id, trackId, url, notification, schedule);
            }
        }
    }
}
