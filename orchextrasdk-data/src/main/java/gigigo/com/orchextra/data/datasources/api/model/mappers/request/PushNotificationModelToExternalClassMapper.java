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

package gigigo.com.orchextra.data.datasources.api.model.mappers.request;

import com.gigigo.ggglib.mappers.ModelToExternalClassMapper;
import com.gigigo.orchextra.domain.model.vo.NotificationPush;
import gigigo.com.orchextra.data.datasources.api.model.requests.ApiNotificationPush;


public class PushNotificationModelToExternalClassMapper
    implements ModelToExternalClassMapper<NotificationPush, ApiNotificationPush> {

  @Override public ApiNotificationPush modelToExternalClass(NotificationPush notificationPush) {

    ApiNotificationPush apiNotificationPush = new ApiNotificationPush();
    apiNotificationPush.setSenderId(notificationPush.getSenderId());
    apiNotificationPush.setToken(notificationPush.getToken());

    return apiNotificationPush;
  }
}
